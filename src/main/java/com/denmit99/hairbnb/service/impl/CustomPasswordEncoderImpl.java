package com.denmit99.hairbnb.service.impl;

import com.denmit99.hairbnb.service.CustomPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomPasswordEncoderImpl implements CustomPasswordEncoder {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String encode(String str) {
        return passwordEncoder.encode(str);
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
