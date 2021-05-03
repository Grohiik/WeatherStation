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
 * @version 0.2.0
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
     * @return Informs that the data has been created on the DB.
     */
    @GetMapping("/test")
    public String testData() {
        DeviceReceiver EriksDevice = new DeviceReceiver("Eriks_Device_1", "Measures weatherdata");

        DataTypeReceiver DataTyp1 =
            new DataTypeReceiver("Number", "Temperature", 0, "C", EriksDevice);
        DataTypeReceiver DataTyp2 = new DataTypeReceiver("Number", "Humidity", 0, "C", EriksDevice);
        DataTypeReceiver DataTyp3 =
            new DataTypeReceiver("Number", "LightLevel", 0, "C", EriksDevice);

        DataReceiver Data1 = new DataReceiver("12.6", "300421", DataTyp1);
        DataReceiver Data2 = new DataReceiver("0.65", "300421", DataTyp2);
        DataReceiver Data3 = new DataReceiver("0.2", "300421", DataTyp3);

        //-----------------------------------------------------------------------------------------------------------

        DeviceReceiver MarcusDevice = new DeviceReceiver("Marcus_Device_1", "Measures weatherdata");

        DataTypeReceiver DataTyp4 =
            new DataTypeReceiver("Number", "Temperature", 0, "C", MarcusDevice);
        DataTypeReceiver DataTyp5 =
            new DataTypeReceiver("Number", "Humidity", 0, "C", MarcusDevice);
        DataTypeReceiver DataTyp6 =
            new DataTypeReceiver("Number", "LightLevel", 0, "C", MarcusDevice);

        DataReceiver Data4 = new DataReceiver("26.3", "300421", DataTyp4);
        DataReceiver Data5 = new DataReceiver("0.95", "300421", DataTyp5);
        DataReceiver Data6 = new DataReceiver("0.8", "300421", DataTyp6);

        //------------------------------------------------------------------------------------------------------------

        DeviceReceiver AdemirsDevice =
            new DeviceReceiver("Ademirs_Device_1", "Measures weatherdata");

        DataTypeReceiver DataTyp7 =
            new DataTypeReceiver("Number", "Temperature", 0, "C", AdemirsDevice);
        DataTypeReceiver DataTyp8 =
            new DataTypeReceiver("Number", "Humidity", 0, "C", AdemirsDevice);
        DataTypeReceiver DataTyp9 =
            new DataTypeReceiver("Number", "LightLevel", 0, "C", AdemirsDevice);

        DataReceiver Data7 = new DataReceiver("40.2", "300421", DataTyp7);
        DataReceiver Data8 = new DataReceiver("0.22", "300421", DataTyp8);
        DataReceiver Data9 = new DataReceiver("0.5", "300421", DataTyp9);

        //------------------------------------------------------------------------------------------------------------

        deviceRepository.saveAll(Arrays.asList(EriksDevice, MarcusDevice, AdemirsDevice));
        dataTypeRepository.saveAll(Arrays.asList(DataTyp1, DataTyp2, DataTyp3, DataTyp4, DataTyp5,
                                                 DataTyp6, DataTyp7, DataTyp8, DataTyp9));
        dataRepository.saveAll(
            Arrays.asList(Data1, Data2, Data3, Data4, Data5, Data6, Data7, Data8, Data9));

        return "Added test data to the DB";
    }

    /**
     * Method to get a list of devices currently on the DB.
     *
     * @return A list of existing devices.
     */
    @GetMapping("/ListDevices")
    public List<DeviceUI> deviceList() {
        List<DeviceUI> deviceUI = new ArrayList<>();
        List<DeviceReceiver> deviceReceiverList = deviceRepository.findAll();

        for (DeviceReceiver deviceReceiver : deviceReceiverList) {
            deviceUI.add(new DeviceUI(deviceReceiver.getDevice(), deviceReceiver.getDescription()));
        }

        return deviceUI;
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
        List<DataTypeUI> dataTypeUI = new ArrayList<>();
        DeviceReceiver deviceReceiver = deviceRepository.findByDevice(deviceName);
        List<DataTypeReceiver> dataTypeReceiverList =
            dataTypeRepository.findAllByDevice_id(deviceReceiver.getId());

        for (DataTypeReceiver dataTypeReceiver : dataTypeReceiverList) {
            dataTypeUI.add(new DataTypeUI(dataTypeReceiver.getName(), dataTypeReceiver.getType(),
                                          dataTypeReceiver.getUnit(), dataTypeReceiver.getCount()));
        }

        return dataTypeUI;
    }

    /**
     * Method to get a list of data from the attached datatype and device, the datatype has to exist
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
        List<DataUI> dataUI = new ArrayList<>();
        DeviceReceiver deviceReceiver = deviceRepository.findByDevice(deviceName);
        List<DataTypeReceiver> dataTypeReceiverList = dataTypeRepository.findAllByName(name);
        List<DataReceiver> dataReceiverList = dataRepository.findAll();

        for (DataTypeReceiver dataTypeReceiver : dataTypeReceiverList) {
            if (deviceReceiver.getId() == dataTypeReceiver.getDeviceId()) {
                for (DataReceiver dataReceiver : dataReceiverList) {
                    if (dataReceiver.getDataTypeId() == dataTypeReceiver.getId()) {
                        dataUI.add(new DataUI(dataReceiver.getValue(), dataReceiver.getCreated()));
                    }
                }
            }
        }

        return dataUI;
    }
}
