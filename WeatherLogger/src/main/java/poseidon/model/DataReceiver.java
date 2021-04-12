package poseidon.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This class receives the data, the data that is specified by the @Column mark.
 * It implements serializable because of it being sent across a stream.
 * @author Marcus Linné
 * @author Erik Kellgren
 *
 * @version 0.0.0
 */
@Entity
@Table(name = "weatherlog")
public class DataReceiver implements Serializable {
    private static final long serialVersionUID = -2343243243242432341L;
    @Id @GeneratedValue(strategy = GenerationType.AUTO) private long id;

    @Column(name = "device") private String device;

    @Column(name = "time") private String time;

    @Column(name = "temperature") private String temperature;

    @Column(name = "humidity") private String humidity;

    @Column(name = "light") private String light;

    /**
     * Totally irrelevant
     */
    protected DataReceiver() {}

    /**
     * Constructor for DataUI, initializes the variables of this class.
     *
     * @param device Which device is the data sent from.
     * @param time Indicates the time when the data was gathered.
     * @param temperature The temperature at the given time.
     * @param humidity The humidity at the given time.
     * @param light The light-level at the given time.
     */
    public DataReceiver(String device, String time, String temperature, String humidity,
                        String light) {
        this.device = device;
        this.time = time;
        this.temperature = temperature;
        this.humidity = humidity;
        this.light = light;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getLight() {
        return light;
    }

    public void setLight(String light) {
        this.light = light;
    }

    /**
     * This is the toString of the class, it formats the print of the class.
     */
    @Override
    public String toString() {
        return String.format(
            "DataReceiver[id=%d, device='%s', time='%s', temperature='%s', humidity='%s', light='%s']",
            id, device, time, temperature, humidity, light);
    }
}
