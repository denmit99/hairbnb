package com.denmit99.hairbnb.service;

public interface CustomPasswordEncoder {
    String encode(String str);

    boolean matches(String rawPassword, String encodedPassword);
}
