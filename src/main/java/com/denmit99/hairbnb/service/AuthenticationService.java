package com.denmit99.hairbnb.service;

import com.denmit99.hairbnb.model.bo.auth.LoginRequestBO;
import com.denmit99.hairbnb.model.bo.auth.LoginResponseBO;
import com.denmit99.hairbnb.model.bo.auth.RegisterRequestBO;
import com.denmit99.hairbnb.model.bo.auth.RegisterResponseBO;

public interface AuthenticationService {
    RegisterResponseBO register(RegisterRequestBO requestBO);

    LoginResponseBO refreshToken(String accessToken, String refreshToken);

    LoginResponseBO login(LoginRequestBO requestBO);

    void logout();

}
