package com.denmit99.hairbnb.service.converter;

import com.denmit99.hairbnb.model.Currency;
import com.denmit99.hairbnb.service.CurrencyConverter;
import com.denmit99.hairbnb.service.ExchangeRateService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class CurrencyConverterImpl implements CurrencyConverter {

    private final ExchangeRateService exchangeRateService;

    public CurrencyConverterImpl(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @Override
    public double convertToDefault(double amount, Currency currency) {
        if (currency == Currency.getDefault()) {
            return amount;
        }
        BigDecimal exchangeRate = exchangeRateService.getLatest(currency);
        return BigDecimal.valueOf(amount).divide(exchangeRate, RoundingMode.HALF_UP).doubleValue();
    }

    @Override
    public double convertFromDefault(double amount, Currency currency) {
        BigDecimal exchangeRate = exchangeRateService.getLatest(currency);
        return BigDecimal.valueOf(amount).multiply(exchangeRate).doubleValue();
    }
}
