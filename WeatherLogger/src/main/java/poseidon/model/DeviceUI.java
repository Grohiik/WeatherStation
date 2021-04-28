package poseidon.model;

/**
 * This class represents what the Device table in the DB contains
 * and which parameters that are included.
 *
 * @author Erik Kellgren
 * @version 0.1.0
 */

public class DeviceUI
{
    private String device;
    private String description;

    public DeviceUI(String device) {
        this.device = device;
    }
    public DeviceUI (String device, String description)
    {
        this.description = description;
        this.device = device;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * The toString method used to sort the data in it's correct order for the DB.
     */
    @Override
    public String toString() {
        return String.format("devices[device='%s', description='%s']", device, description);
    }
}
