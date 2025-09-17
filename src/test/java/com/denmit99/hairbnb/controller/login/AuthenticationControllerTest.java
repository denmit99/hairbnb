package com.denmit99.hairbnb.controller.login;

import com.denmit99.hairbnb.config.filter.RateLimitFilter;
import com.denmit99.hairbnb.model.UserRole;
import com.denmit99.hairbnb.model.bo.auth.LoginResponseBO;
import com.denmit99.hairbnb.model.dto.auth.LoginRequestDTO;
import com.denmit99.hairbnb.model.dto.auth.RegisterRequestDTO;
import com.denmit99.hairbnb.service.AuthenticationService;
import com.denmit99.hairbnb.service.JwtService;
import com.denmit99.hairbnb.service.TokenInfoService;
import com.denmit99.hairbnb.service.UserService;
import com.denmit99.hairbnb.service.impl.JwtProperties;
import com.denmit99.hairbnb.util.RandomEnumUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Locale;
import java.util.function.Consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthenticationControllerTest {

    private static final String AUTH_URI_PREFIX = "/auth";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private TokenInfoService tokenInfoService;

    @MockitoBean
    private AuthenticationService authenticationService;

    @MockitoBean
    private JwtProperties jwtProperties;

    @MockitoBean
    private RateLimitFilter rateLimitFilter;

    @Test
    void logout_InvokesService() throws Exception {
        mockMvc.perform(post(AUTH_URI_PREFIX + "/logout")
                        .with(csrf()))
                .andExpect(status().isOk());
        verify(authenticationService).logout();
    }

    @Test
    void register_Ok() throws Exception {
        testRegisterRequestDtoValidation(dto -> {
        }, status().isOk());
    }

    @Test
    void register_EmailIsNull_Returns400() throws Exception {
        testRegisterRequestDtoValidation(dto -> dto.setEmail(null), status().isBadRequest());
    }

    @Test
    void register_EmailInWrongFormat_Returns400() throws Exception {
        testRegisterRequestDtoValidation(dto -> dto.setEmail(RandomStringUtils.randomAlphanumeric(5)),
                status().isBadRequest());
    }

    @Test
    void register_FirstNameIsNull_Returns400() throws Exception {
        testRegisterRequestDtoValidation(dto -> dto.setFirstName(null), status().isBadRequest());
    }

    @Test
    void register_LastNameIsNull_Returns400() throws Exception {
        testRegisterRequestDtoValidation(dto -> dto.setLastName(null), status().isBadRequest());
    }

    @Test
    void register_PasswordIsNull_Returns400() throws Exception {
        testRegisterRequestDtoValidation(dto -> dto.setPassword(null), status().isBadRequest());
    }

    @Test
    void register_RoleIsNull_Returns400() throws Exception {
        testRegisterRequestDtoValidation(dto -> dto.setRole(null), status().isBadRequest());
    }

    @Test
    void login_Ok() throws Exception {
        when(authenticationService.login(any()))
                .thenReturn(Mockito.mock(LoginResponseBO.class));
        testLoginRequestDtoValidation(dto -> {
        }, status().isOk());
        //verify(authenticationService).login(any());
    }

    @Test
    void login_EmailIsNull_Returns400() throws Exception {
        testLoginRequestDtoValidation(dto -> dto.setEmail(null), status().isBadRequest());
    }

    @Test
    void login_EmailInWrongFormat_Returns400() throws Exception {
        testLoginRequestDtoValidation(dto -> dto.setEmail(RandomStringUtils.randomAlphanumeric(5)),
                status().isBadRequest());
    }

    @Test
    void login_PasswordIsNull_Returns400() throws Exception {
        testLoginRequestDtoValidation(dto -> dto.setPassword(null), status().isBadRequest());
    }

    @Test
    void refresh_Ok() throws Exception {
        var refreshToken = RandomStringUtils.randomAlphanumeric(10);
        mockMvc.perform(post(AUTH_URI_PREFIX + "/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(new Cookie("refresh_token", refreshToken))
                        .with(csrf()))
                .andExpect(status().isOk());
        verify(authenticationService).refreshToken(refreshToken);
    }

    private void testLoginRequestDtoValidation(Consumer<LoginRequestDTO> dtoModifier,
                                               ResultMatcher resultMatcher) throws Exception {
        LoginRequestDTO dto = new LoginRequestDTO();
        dto.setEmail(generateRandomEmail());
        dto.setPassword(RandomStringUtils.randomAlphanumeric(5));
        dtoModifier.accept(dto);
        String requestJson = objectMapper.writeValueAsString(dto);
        mockMvc.perform(post(AUTH_URI_PREFIX + "/login")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(resultMatcher);
    }

    private void testRegisterRequestDtoValidation(Consumer<RegisterRequestDTO> dtoModifier,
                                                  ResultMatcher resultMatcher) throws Exception {
        RegisterRequestDTO dto = createCorrectRegisterRequestDTO();
        dtoModifier.accept(dto);
        String requestJson = objectMapper.writeValueAsString(dto);
        mockMvc.perform(post(AUTH_URI_PREFIX + "/register")
                        .with(csrf())
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(resultMatcher);
    }

    private RegisterRequestDTO createCorrectRegisterRequestDTO() {
        RegisterRequestDTO dto = new RegisterRequestDTO();
        dto.setFirstName(RandomStringUtils.randomAlphabetic(5));
        dto.setLastName(RandomStringUtils.randomAlphabetic(5));
        dto.setEmail(generateRandomEmail());
        dto.setRole(RandomEnumUtils.nextValue(UserRole.class));
        dto.setPassword(RandomStringUtils.randomAlphanumeric(10));
        return dto;
    }

    public static String generateRandomEmail() {
        String username = RandomStringUtils.randomAlphabetic(10);
        String domain = RandomStringUtils.randomAlphabetic(5);
        return username.toLowerCase(Locale.ROOT) + "@" + domain.toLowerCase(Locale.ROOT) + ".com";
    }

}
