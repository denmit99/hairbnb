package com.denmit99.hairbnb.service.converter;

import com.denmit99.hairbnb.model.bo.auth.LoginResponseBO;
import com.denmit99.hairbnb.model.dto.auth.AuthenticationResponseDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LoginResponseBOToAuthenticationResponseDTOConverter
        implements Converter<LoginResponseBO, AuthenticationResponseDTO> {
    @Override
    public AuthenticationResponseDTO convert(LoginResponseBO source) {
        return AuthenticationResponseDTO.builder()
                .token(source.getToken())
                .refreshToken(source.getRefreshToken())
                .build();
    }
}
