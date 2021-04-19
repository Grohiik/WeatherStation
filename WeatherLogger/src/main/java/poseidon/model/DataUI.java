package poseidon.model;

/**
 * This class represents what the sending data contains, what parameters etc are included.
 *
 * @author Marcus Linné
 * @author Erik Kellgren
 * @version 0.0.0
 */
public class DataUI {
    private String device;
    private String time;
    private String temperature;
    private String humidity;
    private String light;
    private String batV;

    /**
     * Constructor for DataUI, initializes the variables of this class.
     *
     * @param device Which device is the data sent from.
     * @param time Indicates the time when the data was gathered.
     * @param temperature The temperature at the given time.
     * @param humidity The humidity at the given time.
     * @param light The light-level at the given time.
     * @param batV The current battery voltage.
     */
    public DataUI(String device, String time, String temperature, String humidity, String light,
                  String batV) {
        this.device = device;
        this.time = time;
        this.temperature = temperature;
        this.humidity = humidity;
        this.light = light;
        this.batV = batV;
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

    public String getBatV() {
        return batV;
    }

    public void setBatV(String batV) {
        this.batV = batV;
    }

    /**
     * The toString method used to sort the data in it's correct order for the DB.
     */
    public String toString() {
        return String.format(
            "weatherlog[device='%s', time='%s', temperature='%s', humidity='%s', light='%s', batV='%s']",
            device, time, temperature, humidity, light, batV);
    }
}
