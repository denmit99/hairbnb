package com.denmit99.hairbnb.service.scheduler;

import com.denmit99.hairbnb.client.ExchangeRateClient;
import com.denmit99.hairbnb.model.Currency;
import com.denmit99.hairbnb.model.entity.ExchangeRate;
import com.denmit99.hairbnb.repository.ExchangeRateRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ExchangeRateScheduler {

    private ExchangeRateClient exchangeRateClient;

    private ExchangeRateRepository repository;

    public ExchangeRateScheduler(ExchangeRateClient exchangeRateClient,
                                 ExchangeRateRepository repository) {
        this.exchangeRateClient = exchangeRateClient;
        this.repository = repository;
    }

    @Scheduled(cron = "${exchange-rate.cron}")
    public void updateRates() {
        var rates = exchangeRateClient.getLatestRates();
        //Currency codes except of default
        var currencies = Arrays.stream(Currency.values())
                .filter(c -> !c.getCode().equals(Currency.getDefault().getCode()))
                .collect(Collectors.toSet());
        ZonedDateTime now = ZonedDateTime.now();
        Set<ExchangeRate> ratesToSave = new HashSet<>();
        for (Currency currency : currencies) {
            ExchangeRate rate = ExchangeRate.builder()
                    .currency(currency)
                    .rate(rates.getRates().get(currency.getCode()))
                    .date(now)
                    .build();
            ratesToSave.add(rate);
        }
        repository.saveAll(ratesToSave);

        //TODO update rates for listings
    }
}
