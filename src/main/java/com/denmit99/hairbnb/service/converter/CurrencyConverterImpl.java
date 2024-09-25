package com.denmit99.hairbnb.service.converter;

import com.denmit99.hairbnb.model.Currency;
import com.denmit99.hairbnb.service.CurrencyConverter;
import org.springframework.stereotype.Component;

@Component
public class CurrencyConverterImpl implements CurrencyConverter {
    //default currency: USD, rates as of 25.09.24
    private static final double EUR_RATE = 1.11;
    private static final double GBP_RATE = 1.33;
    private static final double CNY_RATE = 0.14;
    private static final double JPY_RATE = 0.0069;

    //TODO update current rates daily and recalculate price in usd

    @Override
    public double convertToDefault(double amount, Currency currency) {
        double exchangeRate =  switch(currency) {
            case EUR -> EUR_RATE;
            case GBP -> GBP_RATE;
            case CNY -> CNY_RATE;
            case JPY -> JPY_RATE;
            default -> 1;
        };
        return amount * exchangeRate;
    }
}
