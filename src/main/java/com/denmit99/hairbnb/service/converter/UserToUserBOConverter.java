package com.denmit99.hairbnb.service.converter;

import com.denmit99.hairbnb.model.bo.UserBO;
import com.denmit99.hairbnb.model.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserBOConverter implements Converter<User, UserBO> {
    @Override
    public UserBO convert(User user) {
        return UserBO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .password(user.getPassword())
                .build();
    }
}
