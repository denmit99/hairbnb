package com.denmit99.hairbnb.service.impl;

import com.denmit99.hairbnb.client.ExchangeRateClient;
import com.denmit99.hairbnb.exception.NotFoundException;
import com.denmit99.hairbnb.model.Currency;
import com.denmit99.hairbnb.model.entity.ExchangeRate;
import com.denmit99.hairbnb.repository.ExchangeRateRepository;
import com.denmit99.hairbnb.service.ExchangeRateService;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.event.EventListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private static final String EXCHANGE_RATES_CACHE_NAME = "exchangeRates";

    private static final long UPDATE_RETRY_DELAY = 60_000L;

    public static final Map<String, BigDecimal> INITIAL_RATES = Map.of(
            Currency.USD.getCode(), new BigDecimal("1.1718"),
            Currency.GBP.getCode(), new BigDecimal("0.8646"),
            Currency.JPY.getCode(), new BigDecimal("172.48"),
            Currency.CNY.getCode(), new BigDecimal("8.36")
    );

    private final ExchangeRateRepository repository;

    private ExchangeRateClient exchangeRateClient;

    public ExchangeRateServiceImpl(ExchangeRateRepository repository,
                                   ExchangeRateClient exchangeRateClient) {
        this.repository = repository;
        this.exchangeRateClient = exchangeRateClient;
    }

    @Override
    @Cacheable(value = EXCHANGE_RATES_CACHE_NAME, key = "#currency")
    public BigDecimal getLatest(Currency currency) {
        Optional<ExchangeRate> res = repository.getLatestRate(currency.toString());
        if (res.isEmpty()) {
            throw new NotFoundException("Exchange rate not found for currency: " + currency);
        }
        if (res.get().getDate().isBefore(ZonedDateTime.now().minusDays(1))) {
            log.warn("Exchange rate for currency {} is outdated. Last updated at {}.", currency.name(), res.get().getDate());
        }
        return res.get().getRate();
    }

    @Override
    @Transactional
    @Retryable(retryFor = FeignException.class, backoff = @Backoff(delay = UPDATE_RETRY_DELAY))
    public void updateRates() {
        try {
            var rates = exchangeRateClient.getLatestRates();
            //Currency codes except of default
            updateRatesInternal(rates.getRates());
        } catch (FeignException ex) {
            log.error("Fetching exchange rates failed {}", ex);
            throw ex;
        }
    }

    private void updateRatesInternal(Map<String, BigDecimal> rates) {
        var currencies = Arrays.stream(Currency.values())
                .filter(c -> !c.getCode().equals(Currency.getDefault().getCode()))
                .collect(Collectors.toSet());
        ZonedDateTime now = ZonedDateTime.now();
        Set<ExchangeRate> ratesToSave = new HashSet<>();
        for (Currency currency : currencies) {
            ExchangeRate rate = ExchangeRate.builder()
                    .currency(currency)
                    .rate(rates.get(currency.getCode()))
                    .date(now)
                    .build();
            ratesToSave.add(rate);
        }
        repository.saveAll(ratesToSave);
        //TODO update rates for listings
        clearExchangeRatesCache();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void setInitialRates() {
        updateRatesInternal(INITIAL_RATES);
    }

    @CacheEvict(value = EXCHANGE_RATES_CACHE_NAME, allEntries = true)
    private void clearExchangeRatesCache() {
    }
}
