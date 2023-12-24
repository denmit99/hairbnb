package com.denmit99.hairbnb.controller.login;

import com.denmit99.hairbnb.model.dto.auth.AuthenticationResponseDTO;
import com.denmit99.hairbnb.model.dto.auth.LoginRequestDTO;
import com.denmit99.hairbnb.model.dto.auth.RefreshTokenRequestDTO;
import com.denmit99.hairbnb.model.dto.auth.RegisterRequestDTO;
import com.denmit99.hairbnb.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public AuthenticationResponseDTO login(@Valid @RequestBody LoginRequestDTO requestDTO) {
        return null;
    }

    @PostMapping("/logout")
    public void logout() {
        //TODO
    }

    @PostMapping("/register")
    public AuthenticationResponseDTO register(@Valid @RequestBody RegisterRequestDTO requestDTO) {
        var authResponseBO = authenticationService.register(requestDTO);
        return conversionService.convert(authResponseBO, AuthenticationResponseDTO.class);
    }

    @PostMapping("/refresh")
    public AuthenticationResponseDTO refresh(@Valid @RequestBody RefreshTokenRequestDTO requestDTO) {
        //TODO
        return null;
    }
}
