package poseidon.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.beans.*;
import org.aspectj.bridge.IMessageHandler;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.yaml.snakeyaml.Yaml;

import net.bytebuddy.asm.Advice.This;
import poseidon.controller.WeatherController;
/**
 * @author Eric Lundin
 * @version 1.0.0
 *
 */
public class MqttHandler implements MqttCallback {
    private String connection_url;
    private String subscription;
    private String username;
    private String password;

    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

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

    public void addPropertychangeListener(PropertyChangeListener listener){
        changeSupport.addPropertyChangeListener(listener);
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
        changeSupport.firePropertyChange("propertyName", "", arg1);
    }

    private static MqttConnectOptions setUpConnectOptions(String userName, String password) {
        MqttConnectOptions connectOptions = new MqttConnectOptions();
        connectOptions.setCleanSession(true);
        connectOptions.setUserName(userName);
        connectOptions.setPassword(password.toCharArray());
        return connectOptions;
    }
}