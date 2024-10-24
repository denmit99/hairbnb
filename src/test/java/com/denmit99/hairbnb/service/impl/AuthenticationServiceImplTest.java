package com.denmit99.hairbnb.service.impl;

import com.denmit99.hairbnb.exception.NotFoundException;
import com.denmit99.hairbnb.model.UserToken;
import com.denmit99.hairbnb.model.bo.UserBO;
import com.denmit99.hairbnb.model.bo.auth.LoginRequestBO;
import com.denmit99.hairbnb.model.bo.auth.LoginResponseBO;
import com.denmit99.hairbnb.model.bo.auth.RegisterRequestBO;
import com.denmit99.hairbnb.service.CustomPasswordEncoder;
import com.denmit99.hairbnb.service.JwtService;
import com.denmit99.hairbnb.service.TokenInfoService;
import com.denmit99.hairbnb.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceImplTest {

    private static final int DEFAULT_STRING_SIZE = 5;

    @Mock
    private UserService userService;

    @Mock
    private CustomPasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private ConversionService conversionService;

    @Mock
    private TokenInfoService tokenService;

    @InjectMocks
    private AuthenticationServiceImpl service;

    @Test
    public void register() {
        RegisterRequestBO requestBO = new RegisterRequestBO();
        when(passwordEncoder.encode(requestBO.getPassword()))
                .thenReturn(RandomStringUtils.randomAlphanumeric(DEFAULT_STRING_SIZE));
        UserBO userBO = new UserBO();
        when(userService.create(any()))
                .thenReturn(userBO);
        UserToken userToken = new UserToken();
        when(conversionService.convert(userBO, UserToken.class))
                .thenReturn(userToken);
        String token = RandomStringUtils.randomAlphanumeric(DEFAULT_STRING_SIZE);
        String refreshToken = RandomStringUtils.randomAlphanumeric(DEFAULT_STRING_SIZE);
        when(jwtService.generate(userToken))
                .thenReturn(token);
        when(jwtService.generateRefreshToken(userToken))
                .thenReturn(refreshToken);

        var res = service.register(requestBO);

        assertEquals(token, res.getToken());
        assertEquals(refreshToken, res.getRefreshToken());
    }

    @Test
    public void login() {
        LoginRequestBO requestBO = new LoginRequestBO();
        requestBO.setEmail(RandomStringUtils.randomAlphanumeric(DEFAULT_STRING_SIZE));
        requestBO.setPassword(RandomStringUtils.randomAlphanumeric(DEFAULT_STRING_SIZE));
        UserBO userBO = new UserBO();
        userBO.setId(UUID.randomUUID());
        userBO.setPassword(RandomStringUtils.randomAlphanumeric(DEFAULT_STRING_SIZE));
        when(userService.findByEmail(requestBO.getEmail()))
                .thenReturn(userBO);
        UserToken userToken = new UserToken();
        when(passwordEncoder.matches(requestBO.getPassword(), userBO.getPassword())).thenReturn(true);
        when(conversionService.convert(userBO, UserToken.class))
                .thenReturn(userToken);
        String token = RandomStringUtils.randomAlphanumeric(DEFAULT_STRING_SIZE);
        String refreshToken = RandomStringUtils.randomAlphanumeric(DEFAULT_STRING_SIZE);
        when(jwtService.generate(userToken)).thenReturn(token);
        when(jwtService.generateRefreshToken(userToken)).thenReturn(refreshToken);

        LoginResponseBO res = service.login(requestBO);

        assertNotNull(res);
        assertEquals(token, res.getToken());
        assertEquals(refreshToken, res.getRefreshToken());
        verify(tokenService).revokeAll(userBO.getId());
        verify(tokenService).create(userBO.getId(), token, refreshToken);
    }

    @Test
    public void login_UserEmailNotFound_ThrowsException() {
        LoginRequestBO requestBO = new LoginRequestBO();
        requestBO.setEmail(RandomStringUtils.randomAlphanumeric(DEFAULT_STRING_SIZE));
        when(userService.findByEmail(requestBO.getEmail()))
                .thenReturn(null);

        assertThrows(NotFoundException.class, () -> service.login(requestBO));
    }

    @Test
    public void login_PasswordsDoNotMatch_ThrowsException() {
        LoginRequestBO requestBO = new LoginRequestBO();
        requestBO.setEmail(RandomStringUtils.randomAlphanumeric(DEFAULT_STRING_SIZE));
        requestBO.setPassword(RandomStringUtils.randomAlphanumeric(DEFAULT_STRING_SIZE));
        UserBO userBO = new UserBO();
        userBO.setId(UUID.randomUUID());
        userBO.setPassword(RandomStringUtils.randomAlphanumeric(DEFAULT_STRING_SIZE));
        when(userService.findByEmail(requestBO.getEmail()))
                .thenReturn(userBO);
        when(passwordEncoder.matches(requestBO.getPassword(), userBO.getPassword())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> service.login(requestBO));
    }

}
