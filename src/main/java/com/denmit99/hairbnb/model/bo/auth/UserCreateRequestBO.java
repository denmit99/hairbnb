package com.denmit99.hairbnb.model.bo.auth;

import com.denmit99.hairbnb.model.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCreateRequestBO {
    private String email;

    private String firstName;

    private String lastName;

    private String passwordEncoded;

    private UserRole role;
}
