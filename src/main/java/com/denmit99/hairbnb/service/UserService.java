package com.denmit99.hairbnb.service;

import com.denmit99.hairbnb.model.bo.UserBO;

public interface UserService {
    UserBO findByEmail(String email);
}
