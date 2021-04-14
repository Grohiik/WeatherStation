package poseidon.controller;

import java.io.InputStream;
import java.util.Map;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yaml.snakeyaml.Yaml;
import poseidon.model.DataReceiver;
import poseidon.repository.DataRepository;

/**
 * This class handles the mqtt communication with the embedded system
 *
 * @author Eric Lundin
 * @version 1.0.0
 *
 */
@SpringBootApplication
public class MqttHandler implements MqttCallback {
    private String connection_url;
    private String subscription;
    private String username;
    private String password;

    @Autowired DataRepository dataRepository;

    public MqttHandler() {
        startClient();
    }

    /**
     * Starts and configures the mqtt client
     */
    public void startClient() {
        try {
            optionsReader();
            System.out.println(" == Start Subscriber ==");

            MqttClient client = new MqttClient(connection_url, MqttClient.generateClientId());
            MqttConnectOptions connectOptions = setUpConnectOptions(username, password);

            client.setCallback(this);
            client.connect(connectOptions);
            client.subscribe(subscription);
        } catch (MqttException e) {
            System.err.println(e);
        }
    }

    /**
     * This method reads the options.yml and stores the variables in the respective strings
     */
    private void optionsReader() {
        // TODO use sapmle.yml instead of options.yml
        Yaml yaml = new Yaml();
        InputStream inputStream =
            this.getClass().getClassLoader().getResourceAsStream("application.yml");
        Map<String, Object> obj = yaml.load(inputStream);
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
     * Callback method for when the mqtt client receives a message
     *
     * @param arg0 contains the sender and feed
     * @param arg1 contains the message
     */
    @Override
    public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
        System.out.println(arg0);
        System.out.println(arg1);
        splitStore(arg1.toString());
        System.out.println("next");
    }

    /**
     * Splits the message String in to rows and then columns before storing them in the database
     *
     * @param indata The message string from the mqtt broker
     */
    public void splitStore(String indata) {
        // TODO use the header of the message to arrange the datapoints instead of the order
        var data = indata.split("\\r?\\n");
        for (int i = 1; i < data.length; i++) {
            var utdata = data[i].split(",", 6);
            System.out.println("writing....");
            dataRepository.save(
                new DataReceiver(utdata[0], utdata[1], utdata[2], utdata[3], utdata[4], utdata[5]));
        }
        System.out.println("done");
    }

    /**
     * Creates the connectOptions object used to connect with the mqtt broker
     *
     * @param userName
     * @param password
     * @return
     */
    private static MqttConnectOptions setUpConnectOptions(String userName, String password) {
        MqttConnectOptions connectOptions = new MqttConnectOptions();
        connectOptions.setCleanSession(true);
        connectOptions.setUserName(userName);
        connectOptions.setPassword(password.toCharArray());
        return connectOptions;
    }
}
