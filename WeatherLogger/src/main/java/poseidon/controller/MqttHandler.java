package poseidon.controller;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.crypto.Data;
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
 * @version 0.1.0
 */
@SpringBootApplication
public class MqttHandler implements MqttCallback {
    private String connection_url;
    private String subscription;
    private String username;
    private String password;

    private HashMap<String, DeviceReceiver> devicemap = new HashMap<String, DeviceReceiver>();

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
     * @param arg0 contains the sender and feed.
     * @param arg1 contains the message.
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
     * First the database is checked for existing Devices, if the device exists a new device will
     * not be created and the data will be linked to an existing device with the same device_id.
     * However, if the device doesn't exist, a new object will be created and sent with its
     * corresponding data.
     * The data is related to it's own device by the device_id, shown in the database-table.
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
                        dataRepository.save(
                            new DataReceiver(finalDataString, dataMap.get("time"), dataType));
                    }
                } else {
                    var typeArr = header[headerIndex].split(":");
                    DataTypeReceiver dataTypereceiver =
                        new DataTypeReceiver("number", typeArr[0], 1, dReceiver);
                    dataTypeRepository.save(dataTypereceiver);

                    for (String finalDataString : dataString) {
                        dataRepository.save(new DataReceiver(finalDataString, dataMap.get("time"),
                                                             dataTypereceiver));
                    }
                }
                headerIndex++;
            }

        } else {
            var device = storeDevice(deviceName);

            for (int i = 2; i < header.length; i++) {
                var typeArr = header[i].split(":");
                DataTypeReceiver dataTypeReceiver =
                    new DataTypeReceiver("number", typeArr[0], 1, device);
                dataTypeRepository.save(dataTypeReceiver);

                dataRepository.save(
                    new DataReceiver(dataString[i], dataMap.get("time"), dataTypeReceiver));
            }
        }
    }

    public void storeData(DataTypeReceiver type, String data, String time) {
        dataRepository.save(new DataReceiver(data, time, type));
    }

    public DataTypeReceiver storeType(DeviceReceiver device, String name, String type) {
        DataTypeReceiver dataType = new DataTypeReceiver(type, name, 0, device);
        dataTypeRepository.save(dataType);
        return dataType;
    }

    public DeviceReceiver storeDevice(String deviceName) {
        var device = new DeviceReceiver(deviceName, "n/a");
        deviceRepository.save(device);
        return device;
    }

    /**
     * deviceExists
     * dataTypeExtists
     *
     * @return
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
