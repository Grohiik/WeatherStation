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
 *
 */
@RestController
@RequestMapping(value = "api")
public class WeatherController {
    @Autowired DataRepository dataRepository;
    @Autowired DeviceRepository deviceRepository;

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

        return "much data made";
    }

    @PostMapping("/create")
    public String create(@RequestBody DataUI dataUI) {
        deviceRepository.save(new DeviceReceiver(dataUI.getDevice()));
        dataRepository.save(new DataReceiver(dataUI.getTime(), dataUI.getTemperature(),
                                             dataUI.getHumidity(), dataUI.getLight(),
                                             dataUI.getBatV()));

        return "one is created";
    }

    /**
     * Method used to find all data on the SQL DB,
     * using the GET request with specified mapping.
     *
     * @return all data stored on the DB as JSON
     */
    @GetMapping("/findall")
    public List<DataUI> findAll() {
        List<DataReceiver> data = dataRepository.findAll();
        List<DataUI> dataUI = new ArrayList<>();
        List<DeviceReceiver> aDevices = deviceRepository.findAll();

        for (DeviceReceiver meh : aDevices) {
            for (DataReceiver dataReceiver : data) {
                dataUI.add(new DataUI(meh.getDevice(), dataReceiver.getTime(),
                                      dataReceiver.getTemperature(), dataReceiver.getHumidity(),
                                      dataReceiver.getLight(), dataReceiver.getBatV()));
            }
        }

        return dataUI;
    }

    @RequestMapping("/searchbydevice/{device}")
    public List<DataUI> fetchDataByDevice(@PathVariable String test) {
        List<DeviceReceiver> aDevices = deviceRepository.findByDevice(test);
        List<DataReceiver> data = dataRepository.findByDevice(test);
        List<DataUI> dataUI = new ArrayList<>();

        for (DeviceReceiver meh : aDevices) {
            if (test == meh.getDevice()) {
                for (DataReceiver dataReceiver : data) {
                    dataUI.add(new DataUI(meh.getDevice()));
                    dataUI.add(new DataUI(dataReceiver.getTime(), dataReceiver.getTemperature(),
                                          dataReceiver.getHumidity(), dataReceiver.getLight(),
                                          dataReceiver.getBatV()));
                }
            }
        }
        return dataUI;
    }

    @GetMapping("/getdevices")
    public List<DataUI> getdevices() {
        List<DataUI> dataUI = new ArrayList<>();
        List<DeviceReceiver> aDevices = deviceRepository.findAll();

        for (DeviceReceiver meh : aDevices) {
            dataUI.add(new DataUI(meh.getDevice()));
        }

        return dataUI;
    }

    //    @GetMapping("/test")
    //    public List<DataUI> finder()
    //    {
    //        List<DataReceiver> data = dataRepository.findAllById();
    //        List<DataUI> dataUI = new ArrayList<>();
    //        List<DeviceReceiver> aDevices = deviceRepository.findAllById();
    //
    //        for (DeviceReceiver meh : aDevices) {
    //            dataUI.add(new DataUI(meh.getDevice()));
    //
    //            for (DataReceiver dataReceiver : data) {
    //                dataUI.add(new DataUI(dataReceiver.getTime(), dataReceiver.getTemperature(),
    //                        dataReceiver.getHumidity(), dataReceiver.getLight(),
    //                        dataReceiver.getBatV()));
    //            }
    //        }
    //        return dataUI;
    //    }

    //        @GetMapping("/findbydevice")
    //        public List<DataUI> findByDevice(@RequestParam(required = false) String test) {
    //            List<DataReceiver> data = dataRepository.findByDevice(test);
    //            List<DataUI> dataUI = new ArrayList<>();
    //            List<DeviceReceiver> aDevices = deviceRepository.findByDevice(test);
    //
    //            for (DeviceReceiver meh : aDevices) {
    //                if (meh.getDevice().equals(test))
    //                {
    //                    for (DataReceiver dataReceiver : data)
    //                    {
    //                        dataReceiver.
    //                    }
    //
    //                }
    //
    //            }
    //        }
    //@RequestMapping ("/search/{id}")
//    public String search(@PathVariable long id)
    @GetMapping("/findbydevice")
    @ResponseBody
    public List<DataUI> findByDevice(@RequestParam String requestedname) {
        List<DataReceiver> data = dataRepository.findAll();
        List<DataUI> dataUI = new ArrayList<>();
        List<DeviceReceiver> aDevices = deviceRepository.findAll();

        for (DeviceReceiver meh : aDevices) {
            if (meh.getDevice().equals(requestedname)) {
                for (DataReceiver dataReceiver : data) {
                    dataUI.add(new DataUI(meh.getDevice(), dataReceiver.getTime(),
                                          dataReceiver.getTemperature(), dataReceiver.getHumidity(),
                                          dataReceiver.getLight(), dataReceiver.getBatV()));
                }
                return dataUI;
            }
        }
        return dataUI;
    }
}
