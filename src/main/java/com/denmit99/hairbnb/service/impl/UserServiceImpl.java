package com.denmit99.hairbnb.service.impl;

import com.denmit99.hairbnb.model.UserToken;
import com.denmit99.hairbnb.model.bo.UserBO;
import com.denmit99.hairbnb.model.bo.auth.UserCreateRequestBO;
import com.denmit99.hairbnb.model.entity.User;
import com.denmit99.hairbnb.repository.UserRepository;
import com.denmit99.hairbnb.service.UserService;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    private final ConversionService conversionService;

    public UserServiceImpl(UserRepository repository, ConversionService conversionService) {
        this.repository = repository;
        this.conversionService = conversionService;
    }

    @Override
    public UserBO findByEmail(String email) {
        var userEntity = repository.findByEmail(email);
        return Optional.ofNullable(userEntity)
                .map(u -> conversionService.convert(u, UserBO.class))
                .orElse(null);
    }

    @Override
    public UserBO create(UserCreateRequestBO createRequestBO) {
        var now = ZonedDateTime.now();
        User user = User.builder()
                .id(UUID.randomUUID())
                .firstName(createRequestBO.getFirstName())
                .lastName(createRequestBO.getLastName())
                .email(createRequestBO.getEmail())
                .password(createRequestBO.getPasswordEncoded())
                .role(createRequestBO.getRole())
                .creationDate(now)
                .lastLoginDate(now)
                .build();
        User savedUser = repository.save(user);
        return conversionService.convert(savedUser, UserBO.class);
    }

    @Override
    public UserBO getCurrent() {
        var currentUserToken = (UserToken) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return conversionService.convert(repository.findByEmail(currentUserToken.getEmail()), UserBO.class);
    }

    @Override
    public void updateLastLoginDate(UUID userId, ZonedDateTime lastLoginDate) {
        repository.updateLastLoginDate(userId, lastLoginDate);
    }
}
