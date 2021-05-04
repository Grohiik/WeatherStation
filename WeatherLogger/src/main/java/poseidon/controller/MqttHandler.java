package poseidon.controller;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yaml.snakeyaml.Yaml;
import poseidon.model.DataReceiver;
import poseidon.model.DataTypeReceiver;
import poseidon.model.DeviceReceiver;
import poseidon.repository.DataRepository;
import poseidon.repository.DataTypeRepository;
import poseidon.repository.DeviceRepository;

/**
 * This class handles the mqtt communication with the embedded system.
 *
 * @author Eric Lundin
 * @version 0.2.0
 */
@SpringBootApplication
public class MqttHandler implements MqttCallback {
    private String connection_url;
    private String subscription;
    private String username;
    private String password;

    @Autowired DataRepository dataRepository;
    @Autowired DeviceRepository deviceRepository;
    @Autowired DataTypeRepository dataTypeRepository;

    public MqttHandler() {
        startClient();
    }

    /**
     * Starts and configures the mqtt client.
     */
    public void startClient() {
        try {
            optionsReader();
            System.out.println(" == Start Subscriber ==");

            MqttClient client = new MqttClient(connection_url, MqttClient.generateClientId(),
                                               new MqttDefaultFilePersistence("data"));
            MqttConnectOptions connectOptions = setUpConnectOptions(username, password);

            client.setCallback(this);
            client.connect(connectOptions);
            client.subscribe(subscription);
        } catch (MqttException e) {
            System.err.println(e);
        }
    }

    /**
     * This method reads the application.yml and stores the variables in the respective strings.
     */
    private void optionsReader() {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(
            "application.yml"); // loads the yml file into a map
        Map<String, Object> obj = yaml.load(inputStream);

        // picks the connection variables from the map
        connection_url = (String) obj.get("Host");
        subscription = (String) obj.get("Feed");
        username = (String) obj.get("Username");
        password = (String) obj.get("Key");
    }

    @Override
    public void connectionLost(Throwable arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken arg0) {
        // TODO Auto-generated method stub
    }

    /**
     * Callback method for when the mqtt client receives a message.
     *
     * @param arg0 Contains the sender and feed.
     * @param arg1 Contains the message.
     */
    @Override
    public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
        System.out.println(arg0);
        System.out.println(arg1);
        splitStore(arg1.toString());
        System.out.println("next");
    }

    /**
     * Stores the data from the mqtt broker in the database according to the header in the
     * message(the first line in the message) This function assumes that each line is formatted the
     * same way as the header.
     *
     * @param inData The message string from the mqtt broker.
     */
    public void splitStore(String inData) {
        var lines = inData.split("\\r?\\n"); // splits the message at each line
        var header = lines[0].split(",");    // splits the header at each ","
        var dataString = lines[1].split(",");

        String deviceName = dataString[0];
        String time = dataString[1];
        DeviceReceiver deviceReceiver = new DeviceReceiver(deviceName, "n/a");

        // Checks if the device exists, if not creates and stores a new device
        if (checkDevice(deviceName)) {
            deviceReceiver = getExistingDevice(deviceName);
        } else {
            storeDevice(deviceReceiver);
        }

        // creates a hashmap where DatatypeReceivers for the current device where the types are
        // mapped to the type-names.
        var typeMap = mapDataTypes(deviceReceiver);

        // checks if the datatypes in the header exists, if not creates and stores the new types
        for (int i = 2; i < header.length; i++) {
            var nameAndUnit = header[i].split(":");
            if (!checkDataType(typeMap.get(nameAndUnit[0]), deviceReceiver)) {
                storeType(deviceReceiver, nameAndUnit[0], "number", nameAndUnit[1]);
            }
        }

        // updates the typeMap to represent the current database
        typeMap = mapDataTypes(deviceReceiver);

        // stores the data with appropriate dataTypes
        for (int i = 2; i < header.length; i++) {
            var nameAndUnit = header[i].split(":");
            storeData(typeMap.get(nameAndUnit[0]), dataString[i], time);
        }
    }

    public HashMap<String, DataTypeReceiver> mapDataTypes(DeviceReceiver device) {
        long id = device.getId();

        var typeList = dataTypeRepository.findAllByDevice_id(id);
        HashMap<String, DataTypeReceiver> typeMap = new HashMap<>();

        for (DataTypeReceiver dataTypeReceiver : typeList) {
            typeMap.put(dataTypeReceiver.getName(), dataTypeReceiver);
        }
        return typeMap;
    }

    /**
     * Stores data with a datatype in the database.
     *
     * @param type  The datatype to be sent with the data.
     * @param data  The string containing the data to be sent.
     * @param time  The timestamp to be sent with the data.
     */
    public void storeData(DataTypeReceiver type, String data, String time) {
        DataReceiver dataReceiver = new DataReceiver(data, time, type);
        dataRepository.save(dataReceiver);
    }

    /**
     * Stores and returns a data type in the database.
     *
     * @param device    The device to be sent with the data type.
     * @param name      The name of the datatype.
     * @param type      The type of data sent ie numbers characters etc.
     *
     * @return          Returns a DataTypeReceiver with the aforementioned properties.
     */
    public DataTypeReceiver storeType(DeviceReceiver device, String name, String type,
                                      String unit) {
        long count = 0;
        DataTypeReceiver dataType = new DataTypeReceiver(type, name, count, unit, device);
        dataTypeRepository.save(dataType);
        return dataType;
    }

    /**
     * Stores and returns a device in the database.
     *
     * @param deviceName    The name of the device, every device needs a unique name.
     *
     * @return              Returns a DeviceReceiver with the name deviceName.
     */
    public DeviceReceiver storeDevice(DeviceReceiver device) {
        deviceRepository.save(device);
        return device;
    }

    public DeviceReceiver getExistingDevice(String deviceName) {
        var device = deviceRepository.findByDevice(deviceName);

        return device;
    }
    /**
     * Checks if the device exists in the database.
     *
     * @return  Returns true if the device exists.
     */
    private boolean checkDevice(String deviceName) {
        boolean deviceExists = false;

        List<DeviceReceiver> checkForDevices = deviceRepository.findAll();
        for (DeviceReceiver deviceReceiver : checkForDevices) {
            if (deviceReceiver.getDevice().equals(deviceName)) {
                deviceExists = true;
            }
        }

        return deviceExists;
    }

    /**
     * Checks if a datatype exists in the database.
     *
     * @param dataType  The datatype to be checked.
     * @param device    The device who owns the datatype.
     * @return          Returns true if the datatype exists.
     */
    private boolean checkDataType(DataTypeReceiver dataType, DeviceReceiver device) {
        boolean dataTypeExtists = false;
        long id = device.getId();
        List<DataTypeReceiver> checkForDataTypes = dataTypeRepository.findAllByDevice_id(id);

        for (DataTypeReceiver dataTypeReceiver : checkForDataTypes) {
            if (dataType != null && dataTypeReceiver.getName().equals(dataType.getName())) {
                dataTypeExtists = true;
                break;
            }
        }

        return dataTypeExtists;
    }

    public void store() {}

    /**
     * Creates the connectOptions object used to connect to the mqtt broker.
     *
     * @param userName The username used.
     * @param password The password used.
     *
     * @return returns the connect options.
     */
    private static MqttConnectOptions setUpConnectOptions(String userName, String password) {
        MqttConnectOptions connectOptions = new MqttConnectOptions();
        connectOptions.setCleanSession(true);
        connectOptions.setUserName(userName);
        connectOptions.setPassword(password.toCharArray());
        return connectOptions;
    }
}
