package com.denmit99.hairbnb.service.converter;

import com.denmit99.hairbnb.model.bo.auth.RegisterResponseBO;
import com.denmit99.hairbnb.model.dto.auth.RegisterResponseDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RegisterResponseBOToRegisterResponseDTOConverter
        implements Converter<RegisterResponseBO, RegisterResponseDTO> {
    @Override
    public RegisterResponseDTO convert(RegisterResponseBO source) {
        return RegisterResponseDTO.builder()
                .token(source.getToken())
                .refreshToken(source.getRefreshToken())
                .build();
    }
}
