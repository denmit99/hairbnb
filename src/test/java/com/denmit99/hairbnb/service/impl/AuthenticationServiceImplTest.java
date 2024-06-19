package com.denmit99.hairbnb.service.impl;

import com.denmit99.hairbnb.exception.NotFoundException;
import com.denmit99.hairbnb.model.UserToken;
import com.denmit99.hairbnb.model.bo.UserBO;
import com.denmit99.hairbnb.model.bo.auth.LoginResponseBO;
import com.denmit99.hairbnb.model.dto.auth.LoginRequestDTO;
import com.denmit99.hairbnb.model.dto.auth.RegisterRequestDTO;
import com.denmit99.hairbnb.service.CustomPasswordEncoder;
import com.denmit99.hairbnb.service.JwtService;
import com.denmit99.hairbnb.service.TokenInfoService;
import com.denmit99.hairbnb.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

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
        RegisterRequestDTO requestDTO = new RegisterRequestDTO();
        when(passwordEncoder.encode(requestDTO.getPassword()))
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

        var res = service.register(requestDTO);

        assertEquals(token, res.getToken());
        assertEquals(refreshToken, res.getRefreshToken());
    }

    @Test
    public void login() {
        LoginRequestDTO requestDTO = new LoginRequestDTO();
        requestDTO.setEmail(RandomStringUtils.randomAlphanumeric(DEFAULT_STRING_SIZE));
        UserBO userBO = new UserBO();
        userBO.setId(RandomUtils.nextLong());
        when(userService.findByEmail(requestDTO.getEmail()))
                .thenReturn(userBO);
        UserToken userToken = new UserToken();
        when(conversionService.convert(userBO, UserToken.class))
                .thenReturn(userToken);
        String token = RandomStringUtils.randomAlphanumeric(DEFAULT_STRING_SIZE);
        String refreshToken = RandomStringUtils.randomAlphanumeric(DEFAULT_STRING_SIZE);
        when(jwtService.generate(userToken)).thenReturn(token);
        when(jwtService.generateRefreshToken(userToken)).thenReturn(refreshToken);

        LoginResponseBO res = service.login(requestDTO);

        assertNotNull(res);
        assertEquals(token, res.getToken());
        assertEquals(refreshToken, res.getRefreshToken());
        verify(tokenService).revokeAll(userBO.getId());
        verify(tokenService).create(userBO.getId(), token, refreshToken);
    }

    @Test
    public void loginUserEmailNotFoundThrowsException() {
        LoginRequestDTO requestDTO = new LoginRequestDTO();
        requestDTO.setEmail(RandomStringUtils.randomAlphanumeric(DEFAULT_STRING_SIZE));
        when(userService.findByEmail(requestDTO.getEmail()))
                .thenReturn(null);

        assertThrows(NotFoundException.class, () -> service.login(requestDTO));
    }
}
