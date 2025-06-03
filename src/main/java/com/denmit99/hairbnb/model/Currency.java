package com.denmit99.hairbnb.model;

public enum Currency {

    USD("USD"),
    EUR("EUR"),
    GBP("GBP"),
    JPY("JPY"), //Japanese yen
    CNY("CNY"); //Chinese yuan

    private String code;

    Currency(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code;
    }

    public static Currency getDefault() {
        return EUR;
    }
}
