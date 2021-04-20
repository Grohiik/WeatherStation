# Server and DB info

## DataBase setup/how it works

Our database implements relationships, specifically a OneToMany or ManyToOne
type-relationship. One topic, device in our case, is directly connected to many other selected topics
(time, temp, light, humid). This makes it easier to reach and handle later.

#### 2 tables

    Table 1: Devices
    Table 2: Temperature, humidity, light level, battery voltage, time

These 2 tables are created separately by 2 different classes, one for each table and then linked together.
To indicate which table that should be used or created the annotation ```@Table``` is used.

The annotation ```@Entity``` is used to clarify on both classes creating tables where device table class would look kinda like.
Look into how the DataRepository "interface" is implemented and used by the controller to search.

## Example

### Model classes

#### **DeviceReceiver**
```java
@Entity
@Table(name = "devices")
public class DeviceReceiver implements Serializable
{
    @Serial private static final long serialVersionUID = -2343243243242432341L;
    @Id @GeneratedValue(strategy = GenerationType.AUTO) private long id;
    
    @Column(name = "device") private String device;

    @OneToMany(mappedBy = "device") private Set<DataReceiver> weatherData;
}
```

#### **DataReceiver class**

```java
import poseidon.model.DeviceReceiver;

public class DataReceiver implements Serializable
{
    @Serial private static final long serialVersionUID = -2343243243242432341L;
    @Id @GeneratedValue(strategy = GenerationType.AUTO) private long id;

    @Column(name = "time") private String time;

    @Column(name = "temperature") private String temperature;

    @Column(name = "humidity") private String humidity;

    @Column(name = "light") private String light;

    @Column(name = "batV") private String batV;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "device_id", nullable = false)
    private DeviceReceiver device;
}
```

### Controller classes

#### WeatherController/MqttHandler

## WeatherController/MqttHandler
The MqttHandler.java class communicates with the database and the mqtt broker. 


### Communication with mqtt
Communication with the broker is achieved through a mqtt client set up with Paho: https://www.eclipse.org/paho/index.php?page=clients/java/index.php

#### Setup of the mqtt client
MqttHandler uses the rows "Host" and "Feed" in application.yml to configure wich broker and feed to connect to.
"Username", and "Key" in to generate the connectOptions. This is used to connect and authorize the client towards the broker.
application.yml stores data that is too sensitive to be shipped in the source code.


sample.yml shows how application.yml should be set up.


#### The mqtt client
MqttHandler.java uses a synchronized mqtt client, This is used for simplicitys sake. 
Paho also offer the abillity to set up an asynchronous mqtt client but since MqttHandler is run as a spring app i.e in it's own thread this is not necessary.


```java



```

### Post data
Same as above kinda
