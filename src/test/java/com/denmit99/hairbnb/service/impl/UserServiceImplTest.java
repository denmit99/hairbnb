package com.denmit99.hairbnb.service.impl;

import com.denmit99.hairbnb.model.UserToken;
import com.denmit99.hairbnb.model.bo.UserBO;
import com.denmit99.hairbnb.model.bo.auth.UserCreateRequestBO;
import com.denmit99.hairbnb.model.entity.User;
import com.denmit99.hairbnb.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    private static final int DEFAULT_STRING_SIZE = 5;

    @Mock
    private UserRepository repository;

    @Mock
    private ConversionService conversionService;

    @InjectMocks
    private UserServiceImpl service;

    @Test
    public void findByEmail() {
        String email = RandomStringUtils.randomAlphanumeric(DEFAULT_STRING_SIZE);
        User userEntity = new User();
        when(repository.findByEmail(email))
                .thenReturn(userEntity);
        when(conversionService.convert(userEntity, UserBO.class))
                .thenReturn(new UserBO());

        UserBO res = service.findByEmail(email);

        assertNotNull(res);
        verify(repository).findByEmail(email);
        verify(conversionService).convert(userEntity, UserBO.class);
    }

    @Test
    public void findByEmailNotFoundReturnsNull() {
        String email = RandomStringUtils.randomAlphanumeric(DEFAULT_STRING_SIZE);
        when(repository.findByEmail(email))
                .thenReturn(null);

        UserBO res = service.findByEmail(email);

        assertNull(res);
        verify(repository).findByEmail(email);
    }

    @Test
    public void create() {
        UserCreateRequestBO requestBO = new UserCreateRequestBO();

        service.create(requestBO);

        verify(repository).save(any());
        verify(conversionService).convert(any(), eq(UserBO.class));
    }

    @Test
    public void getCurrent() {
        String email = RandomStringUtils.randomAlphanumeric(DEFAULT_STRING_SIZE);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);
        UserToken userToken = new UserToken();
        userToken.setEmail(email);
        when(authentication.getPrincipal())
                .thenReturn(userToken);
        when(securityContext.getAuthentication())
                .thenReturn(authentication);

        try (MockedStatic<SecurityContextHolder> contextHolder = Mockito.mockStatic(SecurityContextHolder.class)) {
            contextHolder.when(SecurityContextHolder::getContext)
                    .thenReturn(securityContext);
            service.getCurrent();
            verify(repository).findByEmail(email);
            verify(conversionService).convert(any(), eq(UserBO.class));
        }
    }
}
