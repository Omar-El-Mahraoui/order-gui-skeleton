package com.switchfully.vaadin.ordergui.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// copied and adapted code from https://github.com/nielsjani/switchfully-security

//@Import({SecurityConfig.class})
@SpringBootApplication(scanBasePackages = {
        "com.switchfully.vaadin.ordergui.webapp",
        "com.switchfully.vaadin.ordergui.interfaces"
})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}