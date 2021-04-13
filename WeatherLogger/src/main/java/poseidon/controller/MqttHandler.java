package poseidon.controller;

import java.io.InputStream;
import java.util.Map;
import java.beans.*;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.yaml.snakeyaml.Yaml;

import poseidon.model.DataReceiver;
import poseidon.repository.DataRepository;

/**
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

    private String[] testdata = {"lm26", "12.00.15", "20.5", "90", "20"};

    @Autowired DataRepository dataRepository;

    public MqttHandler() {
        startClient();
    }


    /**
     *
     */
    private void optionsReader() {
        Yaml yaml = new Yaml();
        InputStream inputStream =
            this.getClass().getClassLoader().getResourceAsStream("options.yml");
        Map<String, Object> obj = yaml.load(inputStream);
        connection_url = (String) obj.get("Host");
        subscription = (String) obj.get("Feed");
        username = (String) obj.get("Username");
        password = (String) obj.get("Key");
    }
    /**
     *
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
        }
    }

    @Override
    public void connectionLost(Throwable arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
        // TODO Auto-generated method stub
        System.out.println(arg0);
        System.out.println(arg1);
        splitStore(arg1.toString());
        System.out.println("next");
    }

    public void splitStore(String indata){
        var data = indata.split("\\r?\\n");
        for (int i = 1; i < data.length; i++) {
            
            var utdata = data[i].split(",", 6);
            System.out.println("writing....");
            dataRepository.save(new DataReceiver(utdata[0], utdata[1], utdata[2], utdata[3], utdata[4], utdata[5]));
        }
        System.out.println("done");
    }

    private static MqttConnectOptions setUpConnectOptions(String userName, String password) {
        MqttConnectOptions connectOptions = new MqttConnectOptions();
        connectOptions.setCleanSession(true);
        connectOptions.setUserName(userName);
        connectOptions.setPassword(password.toCharArray());
        return connectOptions;
    }
}
