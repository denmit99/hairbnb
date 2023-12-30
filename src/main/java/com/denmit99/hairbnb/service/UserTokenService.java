package com.denmit99.hairbnb.service;

import com.denmit99.hairbnb.model.UserToken;

public interface UserTokenService {
    UserToken findByEmail(String email);
}
