package com.denmit99.hairbnb.service.converter;

import com.denmit99.hairbnb.model.bo.UserBO;
import com.denmit99.hairbnb.model.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserBOConverter implements Converter<User, UserBO> {
    @Override
    public UserBO convert(User source) {
        return UserBO.builder()
                .email(source.getEmail())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .build();
    }
}
