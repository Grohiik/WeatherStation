package poseidon.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import poseidon.repository.DataRepository;
import poseidon.repository.DataTypeRepository;
import poseidon.repository.DeviceRepository;
import poseidon.view.v1.DataTypeUI;
import poseidon.view.v1.DataUI;
import poseidon.view.v1.DeviceUI;

/**
 * Controller class used to handling all different requests that can be made to the server.
 *
 * @author Marcus Linné
 * @author Erik Kellgren
 * @author Linnéa Mörk
 * @author Pratchaya Khansomboon
 * @version 0.3.0
 */
@RestController
@RequestMapping(value = "api")
public class WeatherController {
    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    DataRepository dataRepository;
    @Autowired
    DataTypeRepository dataTypeRepository;

    /**
     * Method to get a list of devices currently on the DB.
     *
     * @return A list of existing devices.
     */
    @GetMapping("/ListDevices")
    public List<DeviceUI> deviceList() {
        final var deviceList = deviceRepository.findAll();
        final List<DeviceUI> devices = new ArrayList<>();
        for (var device : deviceList) devices.add(new DeviceUI(device));
        return devices;
    }

    /**
     * Method to get a list of data types from the attached device.
     *
     * @param deviceName The name of the device.
     *
     * @return A list of data types.
     */
    @GetMapping("/{deviceName}/datatypes")
    public List<DataTypeUI> dataTypeList(@PathVariable String deviceName) {
        final var types = new ArrayList<DataTypeUI>();
        final var device = deviceRepository.findByDeviceName(deviceName);
        if (device != null) {
            final var typeList = dataTypeRepository.findAllByDevice_id(device.getId());
            for (var type : typeList) types.add(new DataTypeUI(type));
            return types;
        }
        return types;
    }

    /**
     * Method to get a list of data from the attached datatype and device, the datatype has to
     exist
     * on the device.
     *
     * @param deviceName    The name of the device.
     * @param name          The type of data.
     *
     * @return A list of data.
     */
    @GetMapping("/{deviceName}/{name}/data")
    public List<DataUI> dataList(@PathVariable("deviceName") String deviceName,
                                 @PathVariable("name") String name) {
        final var datas = new ArrayList<DataUI>();
        final var device = deviceRepository.findByDeviceName(deviceName);
        if (device != null) {
            final var type = dataTypeRepository.findByNameAndDevice_id(name, device.getId());
            if (type != null) {
                final var dataList = dataRepository.findByType_id(type.getId());
                for (var data : dataList) datas.add(new DataUI(data));
            }
        }
        return datas;
    }
}
