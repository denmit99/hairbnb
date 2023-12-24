package com.denmit99.hairbnb.repository;

import com.denmit99.hairbnb.model.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query(value = """
            SELECT * FROM token
            WHERE user_id = :userId AND (revoked = false OR expired = false)
            """, nativeQuery = true)
    List<Token> findAll(Long userId);
}
