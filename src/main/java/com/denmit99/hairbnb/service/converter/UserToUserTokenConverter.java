package com.denmit99.hairbnb.service.converter;

import com.denmit99.hairbnb.model.UserToken;
import com.denmit99.hairbnb.model.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserTokenConverter implements Converter<User, UserToken> {

    @Override
    public UserToken convert(User source) {
        return UserToken.builder()
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .email(source.getEmail())
                .password(source.getPassword())
                .role(source.getRole())
                .build();
    }
}
