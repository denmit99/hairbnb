package com.denmit99.hairbnb.service.scheduler;

import com.denmit99.hairbnb.service.ExchangeRateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ExchangeRateScheduler {

    private ExchangeRateService exchangeRateService;

    public ExchangeRateScheduler(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @Scheduled(cron = "${exchange-rate.cron}")
    public void updateRates() {
        exchangeRateService.updateRates();
    }

}
