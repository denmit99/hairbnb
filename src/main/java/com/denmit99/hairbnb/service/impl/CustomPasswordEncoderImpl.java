package com.denmit99.hairbnb.service.impl;

import com.denmit99.hairbnb.service.CustomPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomPasswordEncoderImpl implements CustomPasswordEncoder {

    private PasswordEncoder passwordEncoder;

    @Override
    public String encode(String str) {
        return passwordEncoder.encode(str);
    }
}
