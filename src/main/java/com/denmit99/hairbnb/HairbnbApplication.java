package com.denmit99.hairbnb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

@SuppressWarnings({"HideUtilityClassConstructor"})
@SpringBootApplication
@EnableScheduling
@EnableFeignClients
@EnableCaching
@EnableRetry
public class HairbnbApplication {

    public static void main(String[] args) {
        SpringApplication.run(HairbnbApplication.class, args);
    }

}
