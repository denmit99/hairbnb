package com.denmit99.hairbnb.service.converter;

import com.denmit99.hairbnb.model.bo.auth.LoginRequestBO;
import com.denmit99.hairbnb.model.dto.auth.LoginRequestDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LoginRequestDTOToLoginRequestBOConverter implements Converter<LoginRequestDTO, LoginRequestBO> {
    @Override
    public LoginRequestBO convert(LoginRequestDTO source) {
        return LoginRequestBO.builder()
                .email(source.getEmail())
                .password(source.getPassword())
                .build();
    }
}
