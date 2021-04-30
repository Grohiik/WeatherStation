# Server and DB info

## DataBase setup/how it works

Our database implements relationships, specifically a OneToMany or ManyToOne
type-relationship. One topic, device in our case, is directly connected to many other selected topics
(time, temperature, light, humidity). This makes it easier to reach and handle later.

#### 2 tables

    Table 1: Devices
    Table 2: Temperature, humidity, light level, battery voltage, time, device_id

**UPDATED**

#### 3 tables more to be added

    Table 1: Devices
    Table 2: DataTypes
    Table 3: Data that is stored

These 3 tables are created separately by 3 different classes, one for each table and then linked together.
To indicate which table that should be used or created the annotation `@Table` is used.

The annotation `@Entity` is used to clarify on both classes creating tables where device table class would look kinda like.
Look into how the DataRepository "interface" is implemented and used by the controller to search.

In the class **DeviceReceiver**, the `@OneToMany(mappedBy = "device")` is used to clarify that the data from column named "device"
is to be related to multiple columns.

In the class **DataTypeReceiver**, the `@ManyToOne(fetch = FetchType.LAZY, optional = false)`
`@JoinColumn(name = "device_id", nullable = false)`

    private DeviceReceiver device;

Is used to make the entries in the DataType table belong to a specific device id. 
It  has a `@OneToMany(mappedBy = "type")` to clarify that the data from the column named "type" is to be related to multiple columns.

In the class **DataReceiver**, the `@ManyToOne(fetch = FetchType.LAZY, optional = false)`
`@JoinColumn(name = "device_id", nullable = false)`
private DeviceReceiver device;`

## Example

### Model classes

#### **DeviceReceiver**

```java
@Entity
@Table(name = "devices")
public class DeviceReceiver implements Serializable
{
    @Serial private static final long serialVersionUID = -2343243243242432341L;
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private long id;
    
    @Column(name = "device") private String device;

    @OneToMany(mappedBy = "device") private Set<DataReceiver> weatherData;

    @Column(name = "description") private String description;
}
```

#### **DataReceiver class**

```java
@Entity
@Table(name = "DATA_STORED")
public class DataReceiver implements Serializable
{
    @Serial private static final long serialVersionUID = -2343243243242432341L;
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private long id;

    @Column(name = "value") private String value;

    @Column(name = "created") private String created;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "type_id", nullable = false)
    private DataTypeReceiver type;
}
```

#### **DeviceUI class**

```java
public class DeviceUI 
{
    private String device;
    private String description;

    public DeviceUI(String device) 
    {
        this.device = device;
    }

    public DeviceUI(String device, String description) 
    {
        this.description = description;
        this.device = device;
    }

    @Override
    public String toString() 
    {
        return String.format("devices[device='%s', description='%s']", device, description);
    }
}
```

#### **DataUI class**

```java
public class DataUI
{
    private String value;
    private String created;

    public DataUI(String value, String created) 
    {
        this.value = value;
        this.created = created;
    }

    @Override
    public String toString() 
    {
        return String.format("data_stored[value='%s', created='%s']", value, created);
    }
}
```

#### **OLD DataUI class**

```java
public class DataUI
{
    private String device;
    private String time;
    private String temperature;
    private String humidity;
    private String light;
    private String batV;

    public DataUI(String device, String time, String temperature, String humidity, String light,
                  String batV)
    {
        this.device = device;
        this.time = time;
        this.temperature = temperature;
        this.humidity = humidity;
        this.light = light;
        this.batV = batV;
    }
}
```
### Repository classes

#### DataRepository

```java
@Repository
public interface DataRepository extends JpaRepository<DataReceiver, Long>
{
    List<DataReceiver> findAll();
}
```

#### DataTypeRepository

```java
@Repository
public interface DataTypeRepository extends JpaRepository<DataTypeReceiver, Long> 
{
    List<DataTypeReceiver> findAll();
    List<DataTypeReceiver> findAllByDevice_id(long id);
    DataTypeReceiver findByType(String type);
}
```

#### DeviceRepository

```java
@Repository
public interface DeviceRepository extends JpaRepository<DeviceReceiver, Long> 
{
    List<DeviceReceiver> findAll();
    DeviceReceiver findByDevice(String device);
}
```

## Controller classes

### WeatherController/MqttHandler

The MqttHandler.java class communicates with the database, and the mqtt broker. 

### Communication with mqtt

Communication with the broker is achieved through a mqtt client set up with Paho: https://www.eclipse.org/paho/index.php?page=clients/java/index.php

#### Setup of the mqtt client

MqttHandler uses the rows "Host" and "Feed" in application.yml to configure which broker and feed to connect to.
"Username", and "Key" in to generate the connectOptions. This is used to connect and authorize the client towards the broker.
application.yml stores data that is too sensitive to be shipped in the source code.

sample.yml shows how the application.yml should be set up.

#### The mqtt client

MqttHandler.java uses a synchronized mqtt client, This is used for simplicity's sake. 
Paho also offer the ability to set up an asynchronous mqtt client but since the MqttHandler is run as a spring app i.e. in its own thread this is not necessary.

```java



```
