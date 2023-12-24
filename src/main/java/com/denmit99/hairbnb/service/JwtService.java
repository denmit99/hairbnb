package com.denmit99.hairbnb.service;

import com.denmit99.hairbnb.model.UserToken;
import com.denmit99.hairbnb.model.bo.UserBO;

/**
 * Know nothing about user, only about token
 */
public interface JwtService {

    String extractEmail(String jwtToken);

    boolean verify(String jwtToken);

    String generate(UserToken token);

    //TODO
    String generateRefreshToken(UserToken token);
}
