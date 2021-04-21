package poseidon.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import poseidon.model.DataReceiver;
import poseidon.model.DataUI;
import poseidon.model.DeviceReceiver;
import poseidon.repository.DataRepository;
import poseidon.repository.DeviceRepository;

/**
 * Controller class used to handling all different requests that can be made to the server.
 *
 * @author Marcus Linné
 * @author Erik Kellgren
 * @author Linnéa Mörk
 * @version 0.1.0
 */
@RestController
@RequestMapping(value = "api")
public class WeatherController {
    @Autowired DeviceRepository deviceRepository;
    @Autowired DataRepository dataRepository;

    /**
     * Method used to create and save data onto the SQL DB,
     * using the GET request with specified mapping.
     *
     * @return informs that the data has been created on the DB
     */
    @GetMapping("/fillWithTrashData")

    public String fillWithTrashData() {
        DeviceReceiver test = new DeviceReceiver("lsgsgs");
        deviceRepository.save(test);
        dataRepository.save(new DataReceiver("12.00.15", "20.5", "90", "20", "3.4", test));

        DeviceReceiver test2 = new DeviceReceiver("lmao");
        DeviceReceiver test3 = new DeviceReceiver("sfs");
        DeviceReceiver test4 = new DeviceReceiver("difisgts");

        deviceRepository.saveAll(Arrays.asList(test2, test3, test4));

        dataRepository.saveAll(
            Arrays.asList(new DataReceiver("12.05.15", "2220.5", "15", "28", "4.2", test2),
                          new DataReceiver("13.15.55", "80.1", "40", "80", "4.4", test3),
                          new DataReceiver("18.01.35", " 60.5", "88", "8", "4", test4)));

        return "Added test data to the DB";
    }

    @PostMapping("/create")
    public String create(@RequestBody DataUI dataUI) {
        deviceRepository.save(new DeviceReceiver(dataUI.getDevice()));
        dataRepository.save(new DataReceiver(dataUI.getTime(), dataUI.getTemperature(),
                                             dataUI.getHumidity(), dataUI.getLight(),
                                             dataUI.getBatV()));

        return "Tables and columns are created";
    }

    /**
     * Method used to find all data on the SQL DB,
     * using the GET request with specified mapping.
     *
     * @return all data stored on the DB as JSON
     */
    @GetMapping("/findall")
    public List<DataUI> findAll() {
        List<DataReceiver> dataReceiverList = dataRepository.findAll();
        List<DeviceReceiver> deviceReceiverList = deviceRepository.findAll();
        List<DataUI> dataUI = new ArrayList<>();

        for (DeviceReceiver deviceReceiver : deviceReceiverList) {
            for (DataReceiver dataReceiver : dataReceiverList) {
                if (dataReceiver.getDeviceId() == deviceReceiver.getId()) {
                    dataUI.add(new DataUI(deviceReceiver.getDevice(), dataReceiver.getTime(),
                                          dataReceiver.getTemperature(), dataReceiver.getHumidity(),
                                          dataReceiver.getLight(), dataReceiver.getBatV()));
                }
            }
        }
        return dataUI;
    }

    @GetMapping("/getdevices")
    public List<DataUI> getdevices() {
        List<DeviceReceiver> deviceReceiverList = deviceRepository.findAll();
        List<DataUI> dataUI = new ArrayList<>();

        for (DeviceReceiver deviceReceiver : deviceReceiverList) {
            dataUI.add(new DataUI(deviceReceiver.getDevice()));
        }
        return dataUI;
    }

    @GetMapping("/findbydevice")
    @ResponseBody
    public List<DataUI> findByDevice(@RequestParam String input) {
        List<DataReceiver> dataReceiverList = dataRepository.findAll();
        List<DeviceReceiver> deviceReceiverList = deviceRepository.findAll();
        List<DataUI> dataUI = new ArrayList<>();

        for (DeviceReceiver deviceReceiver : deviceReceiverList) {
            if (deviceReceiver.getDevice().equals(input)) {
                for (DataReceiver dataReceiver : dataReceiverList) {
                    if (dataReceiver.getDeviceId() == deviceReceiver.getId()) {
                        dataUI.add(new DataUI(deviceReceiver.getDevice(), dataReceiver.getTime(),
                                              dataReceiver.getTemperature(),
                                              dataReceiver.getHumidity(), dataReceiver.getLight(),
                                              dataReceiver.getBatV()));
                    }
                }
                return dataUI;
            }
        }
        return dataUI;
    }
}
