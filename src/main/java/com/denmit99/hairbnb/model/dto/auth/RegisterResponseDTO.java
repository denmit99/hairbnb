package com.denmit99.hairbnb.model.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponseDTO {
    private String token;

    private String refreshToken;
}
