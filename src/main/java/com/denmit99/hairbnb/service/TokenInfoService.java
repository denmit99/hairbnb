package com.denmit99.hairbnb.service;

import com.denmit99.hairbnb.model.entity.TokenInfo;

public interface TokenInfoService {
    TokenInfo findByJWT(String JWT);

    void revokeAll(Long userId);

    void create(Long userId, String token);
}
