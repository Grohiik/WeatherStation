package poseidon.view.v2;

import poseidon.model.DeviceModel;

/**
 * @author Pratchaya Khansomboon
 * @version 0.1.0
 */
public class DeviceView {
    private DeviceModel device;
    public DeviceView(DeviceModel device) { this.device = device; }

    public String getName() { return device.getDeviceName(); }
    public String getCreated_at() { return device.getCreatedAt().toString(); }
}
