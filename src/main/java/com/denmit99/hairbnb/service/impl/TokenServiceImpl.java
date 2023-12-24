package com.denmit99.hairbnb.service.impl;

import com.denmit99.hairbnb.model.entity.Token;
import com.denmit99.hairbnb.repository.TokenRepository;
import com.denmit99.hairbnb.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public void revokeAll(Long userId) {
        var validTokens = tokenRepository.findAll(userId);
        validTokens.forEach(t -> {
            t.setRevoked(true);
            t.setExpired(true);
        });
        tokenRepository.saveAll(validTokens);
    }

    @Override
    public void create(Long userId, String token) {
        var tokenEntity = Token.builder()
                .userId(userId)
                .token(token)
                .isExpired(false)
                .isRevoked(false)
                .build();
        tokenRepository.save(tokenEntity);
    }
}
