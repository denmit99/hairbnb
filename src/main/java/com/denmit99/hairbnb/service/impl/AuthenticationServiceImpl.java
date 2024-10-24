package com.denmit99.hairbnb.service.impl;

import com.denmit99.hairbnb.exception.NotFoundException;
import com.denmit99.hairbnb.model.UserToken;
import com.denmit99.hairbnb.model.bo.UserBO;
import com.denmit99.hairbnb.model.bo.auth.LoginRequestBO;
import com.denmit99.hairbnb.model.bo.auth.LoginResponseBO;
import com.denmit99.hairbnb.model.bo.auth.RegisterRequestBO;
import com.denmit99.hairbnb.model.bo.auth.RegisterResponseBO;
import com.denmit99.hairbnb.model.bo.auth.UserCreateRequestBO;
import com.denmit99.hairbnb.service.AuthenticationService;
import com.denmit99.hairbnb.service.CustomPasswordEncoder;
import com.denmit99.hairbnb.service.JwtService;
import com.denmit99.hairbnb.service.TokenInfoService;
import com.denmit99.hairbnb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomPasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private TokenInfoService tokenService;

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
    public LoginResponseBO refreshToken(String accessToken, String refreshToken) {
        var token = tokenService.findByJWT(accessToken);
        if (token == null || !jwtService.isValid(accessToken)) {
            throw new NotFoundException("Invalid access token");
        }
        if (!token.getRefreshToken().equals(refreshToken) || !jwtService.isValid(refreshToken)) {
            throw new AccessDeniedException("Invalid refresh token");
        }
        var userBO = userService.getCurrent();
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
