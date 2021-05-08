package poseidon.view.v1;

import poseidon.model.DeviceModel;

/**
 * This class represents what the Device table in the DB contains
 * and which parameters that are included.
 *
 * @author Erik Kellgren
 * @author Pratchaya Khansomboon
 * @version 0.1.0
 */

public class DeviceUI {
    private DeviceModel device;
    public DeviceUI(DeviceModel device) { this.device = device; }

    public String getDevice() { return device.getDeviceName(); }
    public String getDescription() { return "n/a"; }
}
