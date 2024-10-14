package com.denmit99.hairbnb.repository;

import com.denmit99.hairbnb.model.entity.TokenInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TokenInfoRepository extends JpaRepository<TokenInfo, UUID> {
    @Query(value = """
            SELECT * FROM token
            WHERE user_id = :userId AND (revoked = false OR expired = false)
            """, nativeQuery = true)
    List<TokenInfo> findAll(UUID userId);

    List<TokenInfo> findByToken(String token);
}
