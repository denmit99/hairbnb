package com.denmit99.hairbnb.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "exchangeRateClient", url = "${exchange-rate.api.url}")
public interface ExchangeRateClient {
    @GetMapping("/v1/latest")
    ExchangeRateResponse getLatestRates();
}
