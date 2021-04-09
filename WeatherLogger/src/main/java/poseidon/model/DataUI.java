package poseidon.model;

/**
 * @author Marcus Linné
 * @author Erik Kellgren
 */
public class DataUI {
    private String device;
    private String time;
    private String temperature;
    private String humidity;
    private String light;

    protected DataUI() {}

    public DataUI(String device, String time, String temperature, String humidity, String light) {
        this.device = device;
        this.time = time;
        this.temperature = temperature;
        this.humidity = humidity;
        this.light = light;
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

    public String toString() {
        return String.format(
            "weatherlog[device='%s', time='%s', temperature='%s', humidity='%s', light='%s']", device,
            time, temperature, humidity, light);
    }
}
