package com.denmit99.hairbnb.service;

import com.denmit99.hairbnb.model.bo.UserBO;
import com.denmit99.hairbnb.model.bo.auth.UserCreateRequestBO;

public interface UserService {
    UserBO findByEmail(String email);

    UserBO create(UserCreateRequestBO createRequestBO);

    UserBO getCurrent();
}
