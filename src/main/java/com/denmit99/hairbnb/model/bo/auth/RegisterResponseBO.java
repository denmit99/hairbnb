package com.denmit99.hairbnb.model.bo.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponseBO {
    private String token;

    private String refreshToken;
}
