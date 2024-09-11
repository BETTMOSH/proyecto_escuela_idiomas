package es.roberto.escuelaidiomas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class EscuelaIdiomasApplication {

    public static void main(String[] args) {
        SpringApplication.run(EscuelaIdiomasApplication.class, args);
    }
}
