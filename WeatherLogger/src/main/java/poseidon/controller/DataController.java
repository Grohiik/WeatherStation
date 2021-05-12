package poseidon.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import poseidon.model.*;
import poseidon.repository.DataRepository;
import poseidon.repository.DataTypeRepository;
import poseidon.repository.DeviceRepository;
import poseidon.view.v2.*;

/**
 * @author Pratchaya Khansomboon
 * @version 0.1.0
 */
@RestController
@RequestMapping(value = "api/v2")
public class DataController {
    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    DataRepository dataRepository;
    @Autowired
    DataTypeRepository dataTypeRepository;

    @GetMapping("/list/devices")
    public List<DeviceView> listDevices() {
        final var deviceList = deviceRepository.findAll();
        final List<DeviceView> devices = new ArrayList<>();
        for (var device : deviceList) devices.add(new DeviceView(device));
        return devices;
    }

    @GetMapping("/list/types/{deviceName}")
    public List<DataTypeView> listDataTypes(@PathVariable String deviceName) {
        final var types = new ArrayList<DataTypeView>();
        final var device = deviceRepository.findByDeviceName(deviceName);
        if (device != null) {
            final var typeList = dataTypeRepository.findAllByDevice_id(device.getId());
            for (var type : typeList) types.add(new DataTypeView(type));
            return types;
        }
        return types;
    }

    @GetMapping("/list/datas/{deviceName}/{typeName}")
    public List<DataView> listDatas(@PathVariable String deviceName, @PathVariable String typeName,
                                    @RequestParam(required = false,
                                                  name = "start") String startDate,
                                    @RequestParam(required = false, name = "end") String endDate,
                                    @RequestParam(required = false,
                                                  name = "latest") String isLatest) {
        final var datas = new ArrayList<DataView>();
        final var device = deviceRepository.findByDeviceName(deviceName);
        if (device != null) {
            final var type = dataTypeRepository.findByNameAndDevice_id(typeName, device.getId());
            if (type != null) {
                List<DataModel> dataList;
                if (startDate == null && endDate == null && isLatest != null
                    && isLatest.equalsIgnoreCase("true")) {
                    dataList = dataRepository.findTop1ByType_idOrderByTimeDesc(type.getId());
                } else if (startDate != null && endDate != null)
                    dataList =
                        dataRepository.findByType_idAndTimeGreaterThanEqualAndTimeLessThanEqual(
                            type.getId(), startDate, endDate);
                else if (startDate != null)
                    dataList = dataRepository.findByType_idAndTimeGreaterThanEqual(type.getId(),
                                                                                   startDate);
                else
                    dataList = dataRepository.findByType_idOrderByTimeAsc(type.getId());
                for (var data : dataList) datas.add(new DataView(data));
            }
        }
        return datas;
    }
}
