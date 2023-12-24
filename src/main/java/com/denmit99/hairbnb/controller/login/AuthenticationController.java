package com.denmit99.hairbnb.controller.login;

import com.denmit99.hairbnb.model.dto.auth.RegisterRequestDTO;
import com.denmit99.hairbnb.model.dto.auth.RegisterResponseDTO;
import com.denmit99.hairbnb.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ConversionService conversionService;

    @PostMapping("/login")
    public void login() {

    }

    @PostMapping("/register")
    public RegisterResponseDTO register(RegisterRequestDTO requestDTO) {
        var authResponseBO = authenticationService.register(requestDTO);
        return conversionService.convert(authResponseBO, RegisterResponseDTO.class);
    }
}
