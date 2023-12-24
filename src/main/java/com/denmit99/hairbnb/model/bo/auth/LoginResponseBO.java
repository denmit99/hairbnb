package com.denmit99.hairbnb.model.bo.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseBO {
    private String token;

    private String refreshToken;
}
