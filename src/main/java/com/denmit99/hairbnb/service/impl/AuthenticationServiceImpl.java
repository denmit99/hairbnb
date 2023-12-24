package com.denmit99.hairbnb.service.impl;

import com.denmit99.hairbnb.exception.NotFoundException;
import com.denmit99.hairbnb.model.UserToken;
import com.denmit99.hairbnb.model.bo.UserBO;
import com.denmit99.hairbnb.model.bo.auth.LoginResponseBO;
import com.denmit99.hairbnb.model.bo.auth.RegisterResponseBO;
import com.denmit99.hairbnb.model.bo.auth.UserCreateRequestBO;
import com.denmit99.hairbnb.model.dto.auth.LoginRequestDTO;
import com.denmit99.hairbnb.model.dto.auth.RefreshTokenRequestDTO;
import com.denmit99.hairbnb.model.dto.auth.RegisterRequestDTO;
import com.denmit99.hairbnb.service.AuthenticationService;
import com.denmit99.hairbnb.service.CustomPasswordEncoder;
import com.denmit99.hairbnb.service.JwtService;
import com.denmit99.hairbnb.service.TokenService;
import com.denmit99.hairbnb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

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
    private TokenService tokenService;

    @Override
    public RegisterResponseBO register(RegisterRequestDTO requestDTO) {
        var createRequest = UserCreateRequestBO.builder()
                .email(requestDTO.getEmail())
                .firstName(requestDTO.getFirstName())
                .lastName(requestDTO.getLastName())
                .passwordEncoded(passwordEncoder.encode(requestDTO.getPassword()))
                .build();
        UserBO userBO = userService.create(createRequest);
        UserToken userToken = conversionService.convert(userBO, UserToken.class);
        String token = jwtService.generate(userToken);
        String refreshToken = jwtService.generateRefreshToken(userToken);
        tokenService.create(userBO.getId(), token);
        return RegisterResponseBO.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public LoginResponseBO refreshToken(RefreshTokenRequestDTO requestDTO) {
        return null;
    }

    @Override
    public LoginResponseBO login(LoginRequestDTO requestDTO) {
        var userBO = userService.findByEmail(requestDTO.getEmail());
        if (userBO == null) {
            throw new NotFoundException(String.format("User with email %s is not found", requestDTO.getEmail()));
        }
        UserToken userToken = conversionService.convert(userBO, UserToken.class);
        var token = jwtService.generate(userToken);
        var refreshToken = jwtService.generateRefreshToken(userToken);
        tokenService.revokeAll(userBO.getId());
        tokenService.create(userBO.getId(), token);
        return LoginResponseBO.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }

}
