package poseidon.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import poseidon.model.DataReceiver;
import poseidon.model.DataUI;
import poseidon.repository.DataRepository;

/**
 *
 *
 *
 * @author Marcus Linné
 * @author Erik Kellgren
 *
 */
@RestController
@RequestMapping(value = "api")
public class WeatherController {
    @Autowired DataRepository dataRepository;

    /**
     * Method used to create and save data onto the SQL DB,
     * using the GET request with specified mapping.
     *
     * @return informs that the data has been created on the DB
     */
    @GetMapping("/fillWithTrashData")
    public String fillWithTrashData() {
        dataRepository.save(new DataReceiver("lm25", "12.00.15", "20.5", "90", "20"));

        dataRepository.saveAll(
            Arrays.asList(new DataReceiver("sfsdfm2325", "12.05.15", "2220.5", "15", "28"),
                          new DataReceiver("bleh", "13.15.55", "80.1", "40", "80"),
                          new DataReceiver("sfsd", "18.01.35", " 60.5", "88", "8")));

        return "much data made";
    }


    @PostMapping("/create")
    public String create(@RequestBody DataUI dataUI) {
        dataRepository.save(new DataReceiver(dataUI.getDevice(), dataUI.getTime(),
                                             dataUI.getTemperature(), dataUI.getHumidity(),
                                             dataUI.getLight()));

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

        for (DataReceiver dataReceiver : data) {
            dataUI.add(new DataUI(dataReceiver.getDevice(), dataReceiver.getTime(),
                                  dataReceiver.getTemperature(), dataReceiver.getHumidity(),
                                  dataReceiver.getLight()));
        }

        return dataUI;
    }
}
