package com.denmit99.hairbnb.client;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor
public class ExchangeRateResponse {
    private double amount;
    private String base;
    private String date;
    private Map<String, BigDecimal> rates;
}
