package com.denmit99.hairbnb.controller.login;

import com.denmit99.hairbnb.constants.Cookies;
import com.denmit99.hairbnb.model.bo.auth.LoginRequestBO;
import com.denmit99.hairbnb.model.bo.auth.LoginResponseBO;
import com.denmit99.hairbnb.model.bo.auth.RegisterRequestBO;
import com.denmit99.hairbnb.model.bo.auth.RegisterResponseBO;
import com.denmit99.hairbnb.model.dto.auth.AuthenticationResponseDTO;
import com.denmit99.hairbnb.model.dto.auth.LoginRequestDTO;
import com.denmit99.hairbnb.model.dto.auth.RegisterRequestDTO;
import com.denmit99.hairbnb.service.AuthenticationService;
import com.denmit99.hairbnb.service.impl.JwtProperties;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final JwtProperties jwtProperties;

    private final AuthenticationService authenticationService;

    private final ConversionService conversionService;

    public AuthenticationController(AuthenticationService authenticationService,
                                    ConversionService conversionService,
                                    JwtProperties jwtProperties) {
        this.authenticationService = authenticationService;
        this.conversionService = conversionService;
        this.jwtProperties = jwtProperties;
    }

    @PostMapping("/login")
    public AuthenticationResponseDTO login(@Valid @RequestBody LoginRequestDTO requestDTO,
                                           HttpServletResponse response) {
        LoginResponseBO loginResponseBO = authenticationService.login(
                conversionService.convert(requestDTO, LoginRequestBO.class));
        Cookie refreshCookie = new Cookie(Cookies.REFRESH_TOKEN, loginResponseBO.getRefreshToken());
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(false); // TODO true in prod
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge((int) jwtProperties.getRefreshExpiration().toSeconds()); // 7 days
        response.addCookie(refreshCookie);
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
    public AuthenticationResponseDTO refresh(@CookieValue(name = Cookies.REFRESH_TOKEN) String refreshToken) {
        LoginResponseBO loginResponseBO = authenticationService.refreshToken(refreshToken);
        return conversionService.convert(loginResponseBO, AuthenticationResponseDTO.class);
    }
}
