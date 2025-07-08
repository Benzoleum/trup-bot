package com.shashla.trup_bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TrupBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrupBotApplication.class, args);
    }

}
