package poseidon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

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

    @Override
    public void run(String... args) throws Exception {
        String sql = "INSERT INTO Test (Time, Data) VALUES ('ss', 'ja')";

       int rows = jdbcTemplate.update(sql);
       if (rows>0) {
           System.out.println("new row created");
       }
    }
}
