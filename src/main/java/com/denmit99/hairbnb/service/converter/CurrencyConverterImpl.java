package com.denmit99.hairbnb.service.converter;

import com.denmit99.hairbnb.model.Currency;
import com.denmit99.hairbnb.service.CurrencyConverter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CurrencyConverterImpl implements CurrencyConverter {
    //default currency: USD, rates as of 25.09.24
    //TODO update current rates daily and recalculate price in usd
    private static final Map<Currency, Double> RATES = Map.of(
            Currency.EUR, 1.11,
            Currency.GBP, 1.33,
            Currency.CNY, 0.14,
            Currency.JPY, 0.0069
    );

    @Override
    public double convertToDefault(double amount, Currency currency) {
        double exchangeRate = RATES.get(currency);
        return amount * exchangeRate;
    }

    @Override
    public double convertFromDefault(double amount, Currency currency) {
        double exchangeRate = RATES.get(currency);
        return amount / exchangeRate;
    }
}
