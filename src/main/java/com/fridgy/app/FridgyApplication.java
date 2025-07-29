package com.fridgy.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class FridgyApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(FridgyApplication.class, args);
        String currentAppName = context.getEnvironment().getProperty("spring.application.name");
        System.out.println("Current application name: " + currentAppName);
    }

}
