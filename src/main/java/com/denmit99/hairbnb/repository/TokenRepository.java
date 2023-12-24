package com.denmit99.hairbnb.repository;

import com.denmit99.hairbnb.model.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
}
