# What to do 

### Recieve 
    2 tables
    Table 1: Device name
    Table 2: Temp, humidity, light, battery voltage, time

    Link these 2 together by separating them in 2 diff classes.

    What is required then?
    We want 2 tables, where we related device to the "table 2".

    Using @table to indicate which table so we can make 2 classes with diff table names. Then the constructors should possibly be updated and work almost like they do now.

    Look into how the datarepository "interface" is implemented and used by the controller to search.

    Use @Entity to clarify on both classes creating tables where device table class would look kinda like. 

## Example

### Model classes

#### **Device class**
```java
    @Entity
    @Table(name = "Devices")
    public class DeviceStuff implements Serializable 
    {
        @Serial 
        private static long id = 2141;
        @Id @GeneratedValue(strategy = GenerationType.AUTO) private long id;

        @Column(name = "device") private String device;
    }_
```

#### **Table class**

```java
    @Entity
    @Table(name = "weatherlog")
    public class DeviceStuff implements Serializable 
    {
        @Serial private static long id = 2141;
        @Id @GeneratedValue(strategy = GenerationType.AUTO) private long id;

        @Column(name = "time") private String time;

        @Column(name = "temperature") private String temperature;

        @Column(name = "humidity") private String humidity;

        @Column(name = "light") private String light;

        @Column(name = "batV") private String batV;
    }
```

### Controller classes

#### WeatherController/MqttController

```java



```

### Post data
Same as above kinda

## DataBase setup/how it works
Our database implements relationships, specifically a OneToMany or ManyToOne
type-relationship. One topic, device in our case, is directly connected to many
other selected topics (time, temp, light, humid). This makes it easier to reach and handle later.

## Example

### Model classes

#### **Device class**

```java

@Entity
@Table(name = "devices")
public class DeviceReceiver implements Serializable
{

    @Serial private static final long serialVersionUID = -2343243243242432341L;
    @Id @GeneratedValue(strategy = GenerationType.AUTO) private long id;

    @Column(name = "device") private String device;
    
    @OneToMany(mappedBy = "device")
    private Set<DataReciever> weatherData;
    
}
```

#### **Data class**

```java
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
    @JoinColumn(name = "device", nullable = false)
    private Device device;
    
}
```