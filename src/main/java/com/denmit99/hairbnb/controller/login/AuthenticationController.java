package com.denmit99.hairbnb.controller.login;

import com.denmit99.hairbnb.model.bo.auth.LoginRequestBO;
import com.denmit99.hairbnb.model.bo.auth.LoginResponseBO;
import com.denmit99.hairbnb.model.bo.auth.RegisterRequestBO;
import com.denmit99.hairbnb.model.bo.auth.RegisterResponseBO;
import com.denmit99.hairbnb.model.dto.auth.AuthenticationResponseDTO;
import com.denmit99.hairbnb.model.dto.auth.LoginRequestDTO;
import com.denmit99.hairbnb.model.dto.auth.RefreshTokenRequestDTO;
import com.denmit99.hairbnb.model.dto.auth.RegisterRequestDTO;
import com.denmit99.hairbnb.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
        LoginResponseBO loginResponseBO = authenticationService.login(
                conversionService.convert(requestDTO, LoginRequestBO.class));
        return conversionService.convert(loginResponseBO, AuthenticationResponseDTO.class);
    }

    @PostMapping("/logout")
    public void logout() {
        authenticationService.logout();
    }

    @PostMapping("/register")
    public AuthenticationResponseDTO register(@Valid @RequestBody RegisterRequestDTO requestDTO) {
        RegisterResponseBO authResponseBO = authenticationService.register(
                conversionService.convert(requestDTO, RegisterRequestBO.class));
        return conversionService.convert(authResponseBO, AuthenticationResponseDTO.class);
    }

    @PostMapping("/refresh")
    public AuthenticationResponseDTO refresh(@RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken,
                                             @Valid @RequestBody RefreshTokenRequestDTO requestDTO) {
        LoginResponseBO loginResponseBO = authenticationService.refreshToken(accessToken.substring("Bearer ".length()),
                requestDTO.getRefreshToken());
        return conversionService.convert(loginResponseBO, AuthenticationResponseDTO.class);
    }
}
