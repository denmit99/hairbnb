package com.denmit99.hairbnb.model.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponseDTO {
    private String token;

    private String refreshToken;
}
