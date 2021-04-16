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

```java



```

### Post data
Same as above kinda