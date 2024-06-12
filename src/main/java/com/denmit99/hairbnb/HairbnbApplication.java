package com.denmit99.hairbnb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public final class HairbnbApplication {

    private HairbnbApplication() {
    }

    public static void main(String[] args) {
        SpringApplication.run(HairbnbApplication.class, args);
    }

}
