package com.denmit99.hairbnb.service.impl;

import com.denmit99.hairbnb.exception.NotFoundException;
import com.denmit99.hairbnb.model.UserToken;
import com.denmit99.hairbnb.model.bo.UserBO;
import com.denmit99.hairbnb.model.bo.auth.*;
import com.denmit99.hairbnb.service.*;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;

    private final CustomPasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final ConversionService conversionService;

    private final TokenInfoService tokenService;

    public AuthenticationServiceImpl(UserService userService,
                                     CustomPasswordEncoder passwordEncoder,
                                     JwtService jwtService,
                                     ConversionService conversionService,
                                     TokenInfoService tokenService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.conversionService = conversionService;
        this.tokenService = tokenService;
    }

    @Override
    public RegisterResponseBO register(RegisterRequestBO requestBO) {
        var createRequest = UserCreateRequestBO.builder()
                .email(requestBO.getEmail())
                .firstName(requestBO.getFirstName())
                .lastName(requestBO.getLastName())
                .role(requestBO.getRole())
                .passwordEncoded(passwordEncoder.encode(requestBO.getPassword()))
                .build();
        UserBO userBO = userService.create(createRequest);
        UserToken userToken = conversionService.convert(userBO, UserToken.class);
        String token = jwtService.generate(userToken);
        String refreshToken = jwtService.generateRefreshToken(userToken);
        tokenService.create(userBO.getId(), token, refreshToken);
        return RegisterResponseBO.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public LoginResponseBO refreshToken(String refreshToken) {
        var token = tokenService.findByRefreshToken(refreshToken);
        if (token == null || !jwtService.isValid(refreshToken)) {
            throw new AccessDeniedException("Invalid refresh token");
        }
        var email = jwtService.extractEmail(refreshToken);
        var userBO = userService.findByEmail(email);
        return updateTokens(userBO);
    }

    @Override
    public LoginResponseBO login(LoginRequestBO requestBO) {
        var userBO = userService.findByEmail(requestBO.getEmail());
        if (userBO == null || !passwordEncoder.matches(requestBO.getPassword(), userBO.getPassword())) {
            throw new NotFoundException("User with provided e-mail or password is not found");
        }
        var newTokens = updateTokens(userBO);
        userService.updateLastLoginDate(userBO.getId(), ZonedDateTime.now());
        return newTokens;
    }

    @Override
    public void logout() {
        var userBO = userService.getCurrent();
        tokenService.revokeAll(userBO.getId());
    }

    private LoginResponseBO updateTokens(UserBO userBO) {
        UserToken userToken = conversionService.convert(userBO, UserToken.class);
        var token = jwtService.generate(userToken);
        var refreshToken = jwtService.generateRefreshToken(userToken);
        tokenService.revokeAll(userBO.getId());
        tokenService.create(userBO.getId(), token, refreshToken);
        return LoginResponseBO.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }
}
