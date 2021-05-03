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

        var deviceName = dataString[0];

        HashMap<String, String> dataMap = new HashMap<String, String>();

        if (header.length == dataString.length) {
            for (int j = 0; j < header.length; j++) {
                dataMap.put(header[j], dataString[j]);
            }
        }

        int headerIndex = 2;
        if (checkDevice(header[0])) {
            DeviceReceiver dReceiver = deviceRepository.findByDevice(header[0]);
            long id = dReceiver.getId();

            for (int i = 0; i < header.length && headerIndex <= header.length; i++) {
                if (checkDataType(header[headerIndex], id)) {
                    for (String finalDataString : dataString) {
                        var dataType = dataTypeRepository.findByType(finalDataString);
                        storeData(dataType, finalDataString, dataMap.get("time"));
                    }
                } else {
                    var typeArr = header[headerIndex].split(":");
                    var dataTypeReceiver = storeType(dReceiver, typeArr[0], typeArr[1], "number");

                    for (String finalDataString : dataString) {
                        storeData(dataTypeReceiver, finalDataString, dataMap.get("time"));
                    }
                }
                headerIndex++;
            }

        } else {
            var device = storeDevice(deviceName);

            for (int i = 2; i < header.length; i++) {
                var typeArr = header[i].split(":");
                var dataTypeReceiver = storeType(device, typeArr[0], typeArr[1], "number");
                storeData(dataTypeReceiver, dataString[i], dataMap.get("time"));
            }
        }
    }

    /**
     * Stores data with a datatype in the database.
     *
     * @param type  The datatype to be sent with the data.
     * @param data  The string containing the data to be sent.
     * @param time  The timestamp to be sent with the data.
     */
    public void storeData(DataTypeReceiver type, String data, String time) {
        dataRepository.save(new DataReceiver(data, time, type));
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
        // TODO add unit ie m, m/s etc
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
    public DeviceReceiver storeDevice(String deviceName) {
        var device = new DeviceReceiver(deviceName, "n/a");
        deviceRepository.save(device);
        return device;
    }

    /**
     * Checks if the device exists in the database.
     *
     * @return  Returns true if the device exists.
     */
    private boolean checkDevice(String device) {
        boolean deviceExists = false;

        List<DeviceReceiver> checkForDevices = deviceRepository.findAll();
        for (DeviceReceiver deviceReceiver : checkForDevices) {
            if (deviceReceiver.getDevice().equals(device)) {
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
    private boolean checkDataType(String dataType, long device) {
        List<DataTypeReceiver> checkForDataTypes = dataTypeRepository.findAllByDevice_id(device);

        boolean dataTypeExtists = checkForDataTypes.contains(dataType);

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
