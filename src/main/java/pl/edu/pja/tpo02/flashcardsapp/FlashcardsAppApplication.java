package pl.edu.pja.tpo02.flashcardsapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@ConfigurationPropertiesScan
public class FlashcardsAppApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(FlashcardsAppApplication.class, args);

        FlashcardsController controller = context.getBean(FlashcardsController.class);
        controller.displayMenu();
    }

}