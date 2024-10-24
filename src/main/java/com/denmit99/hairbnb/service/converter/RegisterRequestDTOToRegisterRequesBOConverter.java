package com.denmit99.hairbnb.service.converter;

import com.denmit99.hairbnb.model.bo.auth.RegisterRequestBO;
import com.denmit99.hairbnb.model.dto.auth.RegisterRequestDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RegisterRequestDTOToRegisterRequesBOConverter implements Converter<RegisterRequestDTO, RegisterRequestBO> {
    @Override
    public RegisterRequestBO convert(RegisterRequestDTO source) {
        return RegisterRequestBO.builder()
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .email(source.getEmail())
                .password(source.getPassword())
                .role(source.getRole())
                .build();
    }
}
