package com.denmit99.hairbnb.service.impl;

import com.denmit99.hairbnb.model.entity.TokenInfo;
import com.denmit99.hairbnb.repository.TokenInfoRepository;
import com.denmit99.hairbnb.service.TokenInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenInfoServiceImpl implements TokenInfoService {

    @Autowired
    private TokenInfoRepository tokenInfoRepository;

    @Override
    public TokenInfo findByJWT(String token) {
        return tokenInfoRepository.findByToken(token).get(0);
    }

    @Override
    public void revokeAll(Long userId) {
        var validTokens = tokenInfoRepository.findAll(userId);
        validTokens.forEach(t -> {
            t.setRevoked(true);
            t.setExpired(true);
        });
        tokenInfoRepository.saveAll(validTokens);
    }

    @Override
    public void create(Long userId, String token) {
        var tokenEntity = TokenInfo.builder()
                .userId(userId)
                .token(token)
                .isExpired(false)
                .isRevoked(false)
                .build();
        tokenInfoRepository.save(tokenEntity);
    }
}
