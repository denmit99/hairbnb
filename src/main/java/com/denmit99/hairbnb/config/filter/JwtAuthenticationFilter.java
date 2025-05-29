package com.denmit99.hairbnb.config.filter;

import com.denmit99.hairbnb.constants.Headers;
import com.denmit99.hairbnb.model.UserToken;
import com.denmit99.hairbnb.service.JwtService;
import com.denmit99.hairbnb.service.TokenInfoService;
import com.denmit99.hairbnb.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.convert.ConversionService;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtService jwtService;

    private final UserService userService;

    private final TokenInfoService tokenInfoService;

    private final ConversionService conversionService;

    public JwtAuthenticationFilter(JwtService jwtService,
                                   UserService userService,
                                   TokenInfoService tokenInfoService,
                                   ConversionService conversionService) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.tokenInfoService = tokenInfoService;
        this.conversionService = conversionService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain)
            throws ServletException, IOException {
        String authHeader = request.getHeader(Headers.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        String tokenString = authHeader.substring(BEARER_PREFIX.length());
        var email = jwtService.extractEmail(tokenString);
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var userBO = userService.findByEmail(email);
            if (userBO == null) {
                throw new AccessDeniedException("User with the provided e-mail doesn't exist");
            }
            var token = tokenInfoService.findByJWT(tokenString);
            var userToken = conversionService.convert(userBO, UserToken.class);
            if (jwtService.isValid(tokenString) && !token.isRevoked() && !token.isExpired()) {
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        userToken, null, userToken.getAuthorities()
                );
                context.setAuthentication(auth);
                SecurityContextHolder.setContext(context);
            }
        }
        chain.doFilter(request, response);
    }
}
