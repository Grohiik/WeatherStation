package poseidon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * WeatherApp is the main Application for start up.
 *
 * @author Pratchaya Khansomboon
 * @version 0.0.0
 */
@SpringBootApplication
public class WeatherApp {
    public static void main(String[] args) {
        var app = new SpringApplication(WeatherApp.class);
        app.run(args);
    }
}
