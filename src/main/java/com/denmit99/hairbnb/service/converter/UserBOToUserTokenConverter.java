package com.denmit99.hairbnb.service.converter;

import com.denmit99.hairbnb.model.UserToken;
import com.denmit99.hairbnb.model.bo.UserBO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserBOToUserTokenConverter implements Converter<UserBO, UserToken> {
    @Override
    public UserToken convert(UserBO userBO) {
        return UserToken.builder()
                .email(userBO.getEmail())
                .firstName(userBO.getFirstName())
                .lastName(userBO.getLastName())
                .password(userBO.getPassword())
                .build();
    }
}
