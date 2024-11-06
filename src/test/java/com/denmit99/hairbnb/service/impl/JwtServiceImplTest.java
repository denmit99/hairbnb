package com.denmit99.hairbnb.service.impl;

import com.denmit99.hairbnb.model.UserRole;
import com.denmit99.hairbnb.model.UserToken;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class JwtServiceImplTest {

    @InjectMocks
    private JwtServiceImpl jwtService;

    @Test
    void generate() {
        var base64 = generateRandomBase64String(256);
        ReflectionTestUtils.setField(jwtService, "secretKey", base64);
        ReflectionTestUtils.setField(jwtService, "expiration", Duration.ofMinutes(15));
        ReflectionTestUtils.setField(jwtService, "refreshExpiration", Duration.ofDays(7));
        var userToken = UserToken.builder()
                .firstName(RandomStringUtils.randomAlphabetic(5))
                .lastName(RandomStringUtils.randomAlphabetic(5))
                .email(RandomStringUtils.randomAlphanumeric(5))
                .role(UserRole.USER)
                .build();

        var res = jwtService.generate(userToken);

        assertNotNull(res);
        assertEquals(userToken.getEmail(), jwtService.extractEmail(res));
    }

    private String generateRandomBase64String(int length) {
        byte[] randomBytes = new byte[length];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(randomBytes);
        return Base64.getEncoder().encodeToString(randomBytes);
    }
}
