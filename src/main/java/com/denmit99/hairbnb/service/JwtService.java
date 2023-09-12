package com.denmit99.hairbnb.service;

import com.denmit99.hairbnb.model.UserToken;

public interface JwtService {

    UserToken extractUser(String jwtToken);

    String extractEmail(String jwtToken);

    boolean verify(String jwtToken);

    String generate(UserToken token);
}
