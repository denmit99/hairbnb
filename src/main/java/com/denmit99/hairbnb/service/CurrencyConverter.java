package com.denmit99.hairbnb.service;

import com.denmit99.hairbnb.model.Currency;

public interface CurrencyConverter {
    double convertToDefault(double amount, Currency currency);
}
