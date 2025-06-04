package com.denmit99.hairbnb.service;

import com.denmit99.hairbnb.model.Currency;

import java.math.BigDecimal;

public interface ExchangeRateService {
    BigDecimal getLatest(Currency currency);

    void updateRates();
}
