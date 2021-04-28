package poseidon.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import poseidon.model.*;
import poseidon.repository.DataRepository;
import poseidon.repository.DataTypeRepository;
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
    @Autowired DataTypeRepository dataTypeRepository;

    /**
     * Method used to create and save data onto the SQL DB,
     * using the GET request with specified mapping.
     *
     * @return informs that the data has been created on the DB
     */
    @GetMapping("/fillWithTrashData")

    public String fillWithTrashData() {
        DeviceReceiver test = new DeviceReceiver("lsgsgs", "sdkfsdfksdjfksl");
        DataTypeReceiver hejsvejs = new DataTypeReceiver("123", "Erik", 234465462, test);
        deviceRepository.save(test);
        dataTypeRepository.save(hejsvejs);
        dataRepository.save(new DataReceiver("1234", "230501", hejsvejs));

        DeviceReceiver test2 = new DeviceReceiver("lmao", "inget viktigt");
        DeviceReceiver test3 = new DeviceReceiver("sfs", "inget viktigt2");
        DeviceReceiver test4 = new DeviceReceiver("difisgts", "inget viktigt3");
        DataTypeReceiver hejsvejs2 = new DataTypeReceiver("12334435", "Erik2", 234462, test2);
        DataTypeReceiver hejsvejs3 = new DataTypeReceiver("12435343", "Erik3", 23462, test3);
        DataTypeReceiver hejsvejs4 = new DataTypeReceiver("1234523", "Erik4", 235462, test4);

        deviceRepository.saveAll(Arrays.asList(test2, test3, test4));
        dataTypeRepository.saveAll(Arrays.asList(hejsvejs2, hejsvejs3, hejsvejs4));

        dataRepository.saveAll(
            Arrays.asList(new DataReceiver("123", "090812", hejsvejs2),
                          new DataReceiver("567", "121121", hejsvejs3),
                          new DataReceiver("999", "010104", hejsvejs4)));

        return "Added test data to the DB";
    }


    @GetMapping("/ListDevices")

    public List<DeviceUI> listDevices()
    {
        List<DeviceUI> dataUI = new ArrayList<>();
        List<DeviceReceiver> devices = deviceRepository.findAll();

        for(DeviceReceiver deviceList : devices)
        {
            dataUI.add(new DeviceUI(deviceList.getDevice()));
        }
        return dataUI;
    }

    @GetMapping("/{deviceName}/datatypes")

    public List<DataTypeUI> listContainingDataTypes (@PathVariable String deviceName)
    {
          List<DataTypeUI> theList = new ArrayList<>();


        DeviceReceiver hej = deviceRepository.findByDevice("lmao");
        List<DataTypeReceiver> types = dataTypeRepository.findAllByDevice_id(hej.getId());
        for(DataTypeReceiver hejsvejs : types)
        {
            theList.add(new DataTypeUI(hejsvejs.getName(), hejsvejs.getType(), hejsvejs.getCount()));
        }
        System.out.println(new DeviceUI(hej.getDevice(),hej.getDescription()));

        return theList;
    }
/*
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
    }*/
}
