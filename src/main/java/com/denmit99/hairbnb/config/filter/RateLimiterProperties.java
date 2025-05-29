package com.denmit99.hairbnb.config.filter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Data
@Component
@ConfigurationProperties(prefix = "rate-limiter")
public class RateLimiterProperties {
    private Long bucketCapacity;
    private Long refillRate;
    private Duration refillPeriod;
}
