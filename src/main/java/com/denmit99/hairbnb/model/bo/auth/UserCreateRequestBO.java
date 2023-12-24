package com.denmit99.hairbnb.model.bo.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCreateRequestBO {
    private String email;

    private String firstName;

    private String lastName;

    private String passwordEncoded;
}
