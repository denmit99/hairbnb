package com.denmit99.hairbnb.service.impl;

import com.denmit99.hairbnb.model.entity.TokenInfo;
import com.denmit99.hairbnb.repository.TokenInfoRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TokenInfoServiceImplTest {

    private static final int DEFAULT_STRING_SIZE = 5;

    @Mock
    private TokenInfoRepository tokenInfoRepository;

    @InjectMocks
    private TokenInfoServiceImpl service;

    @Test
    public void findByJWT() {
        String token = RandomStringUtils.randomAlphanumeric(DEFAULT_STRING_SIZE);
        TokenInfo tokenInfo = new TokenInfo();
        Mockito.when(tokenInfoRepository.findByToken(token))
                .thenReturn(List.of(tokenInfo));

        TokenInfo res = service.findByJWT(token);

        assertEquals(tokenInfo, res);
        verify(tokenInfoRepository).findByToken(token);
    }

    @Test
    public void revokeAll() {
        Long userId = RandomUtils.nextLong();
        List<TokenInfo> tokenInfoList = List.of(new TokenInfo());
        when(tokenInfoRepository.findAll(userId))
                .thenReturn(tokenInfoList);

        service.revokeAll(userId);

        verify(tokenInfoRepository).findAll(userId);
        verify(tokenInfoRepository).saveAll(tokenInfoList);
    }

    @Test
    public void create() {
        Long userId = RandomUtils.nextLong();
        String token = RandomStringUtils.randomAlphanumeric(DEFAULT_STRING_SIZE);
        String refreshToken = RandomStringUtils.randomAlphanumeric(DEFAULT_STRING_SIZE);

        service.create(userId, token, refreshToken);

        TokenInfo expectedTokenInfo = TokenInfo.builder()
                .userId(userId)
                .token(token)
                .refreshToken(refreshToken)
                .isExpired(false)
                .isRevoked(false)
                .build();
        verify(tokenInfoRepository).save(eq(expectedTokenInfo));
    }

}
