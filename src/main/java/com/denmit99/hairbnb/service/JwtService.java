package com.denmit99.hairbnb.service;

import com.denmit99.hairbnb.model.UserToken;

/**
 * Know nothing about user, only about token
 */
public interface JwtService {

    String extractEmail(String jwtToken);

    boolean isValid(String jwtToken);

    String generate(UserToken token);

    String generateRefreshToken(UserToken token);
}
