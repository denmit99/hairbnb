package com.denmit99.hairbnb.service;

public interface TokenService {
    void revokeAll(Long userId);

    void create(Long userId, String token);
}
