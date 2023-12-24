package com.denmit99.hairbnb.service.converter;

import com.denmit99.hairbnb.model.bo.auth.RegisterResponseBO;
import com.denmit99.hairbnb.model.dto.auth.AuthenticationResponseDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RegisterResponseBOToAuthenticationResponseDTOConverter
        implements Converter<RegisterResponseBO, AuthenticationResponseDTO> {
    @Override
    public AuthenticationResponseDTO convert(RegisterResponseBO source) {
        return AuthenticationResponseDTO.builder()
                .token(source.getToken())
                .refreshToken(source.getRefreshToken())
                .build();
    }
}
