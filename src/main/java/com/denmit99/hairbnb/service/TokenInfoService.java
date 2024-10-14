package com.denmit99.hairbnb.service;

import com.denmit99.hairbnb.model.entity.TokenInfo;

import java.util.UUID;

public interface TokenInfoService {
    TokenInfo findByJWT(String token);

    void revokeAll(UUID userId);

    void create(UUID userId, String token, String refreshToken);
}
