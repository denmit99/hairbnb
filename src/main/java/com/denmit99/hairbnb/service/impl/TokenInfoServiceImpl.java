package com.denmit99.hairbnb.service.impl;

import com.denmit99.hairbnb.model.entity.TokenInfo;
import com.denmit99.hairbnb.repository.TokenInfoRepository;
import com.denmit99.hairbnb.service.TokenInfoService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenInfoServiceImpl implements TokenInfoService {

    private final TokenInfoRepository tokenInfoRepository;

    public TokenInfoServiceImpl(TokenInfoRepository tokenInfoRepository) {
        this.tokenInfoRepository = tokenInfoRepository;
    }

    @Override
    public TokenInfo findByJWT(String token) {
        return tokenInfoRepository.findByToken(token).get(0);
    }

    @Override
    public void revokeAll(UUID userId) {
        var validTokens = tokenInfoRepository.findAll(userId);
        validTokens.forEach(t -> {
            t.setRevoked(true);
            t.setExpired(true);
        });
        tokenInfoRepository.saveAll(validTokens);
    }

    @Override
    public void create(UUID userId, String token, String refreshToken) {
        var tokenEntity = TokenInfo.builder()
                .userId(userId)
                .token(token)
                .refreshToken(refreshToken)
                .isExpired(false)
                .isRevoked(false)
                .build();
        tokenInfoRepository.save(tokenEntity);
    }
}
