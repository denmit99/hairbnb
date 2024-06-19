package com.denmit99.hairbnb.service;

import com.denmit99.hairbnb.model.bo.auth.LoginResponseBO;
import com.denmit99.hairbnb.model.bo.auth.RegisterResponseBO;
import com.denmit99.hairbnb.model.dto.auth.LoginRequestDTO;
import com.denmit99.hairbnb.model.dto.auth.RegisterRequestDTO;

public interface AuthenticationService {
    RegisterResponseBO register(RegisterRequestDTO requestDTO);

    LoginResponseBO refreshToken(String accessToken, String refreshToken);

    LoginResponseBO login(LoginRequestDTO requestDTO);

    void logout();

}
