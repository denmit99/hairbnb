package com.denmit99.hairbnb.repository;

import com.denmit99.hairbnb.model.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, UUID> {
    @Query(value = """
            SELECT *
            FROM public.exchange_rate_eur
            WHERE currency = :currency
            ORDER BY date DESC
            LIMIT 1;
            """, nativeQuery = true)
    Optional<ExchangeRate> getLatestRate(String currency);
}
