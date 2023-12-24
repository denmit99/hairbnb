package com.denmit99.hairbnb.service.impl;

import com.denmit99.hairbnb.model.bo.UserBO;
import com.denmit99.hairbnb.model.bo.auth.UserCreateRequestBO;
import com.denmit99.hairbnb.model.entity.User;
import com.denmit99.hairbnb.repository.UserRepository;
import com.denmit99.hairbnb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ConversionService conversionService;

    @Override
    public UserBO findByEmail(String email) {
        return conversionService.convert(repository.findByEmail(email), UserBO.class);
    }

    @Override
    public UserBO create(UserCreateRequestBO createRequestBO) {
        User user = User.builder()
                .firstName(createRequestBO.getFirstName())
                .lastName(createRequestBO.getLastName())
                .email(createRequestBO.getEmail())
                .password(createRequestBO.getPasswordEncoded())
                .creationDate(ZonedDateTime.now())
                .build();
        User savedUser = repository.save(user);
        return conversionService.convert(savedUser, UserBO.class);
    }
}
