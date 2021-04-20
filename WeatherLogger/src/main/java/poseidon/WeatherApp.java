package poseidon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import poseidon.repository.DataRepository;

/**
 * WeatherApp is the main Application for start up.
 *
 * @author Marcus Linné
 * @version 0.1.0
 */
@SpringBootApplication
public class WeatherApp {
    @Autowired DataRepository dataRepository;
    public static void main(String[] args) {
        SpringApplication.run(WeatherApp.class, args);
    }
}
