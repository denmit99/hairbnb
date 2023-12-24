package com.denmit99.hairbnb.service.impl;

import com.denmit99.hairbnb.model.UserToken;
import com.denmit99.hairbnb.model.bo.UserBO;
import com.denmit99.hairbnb.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${app.jwt.secret}")
    private String secretKey;

    @Value("${app.jwt.expiration}")
    private Duration expiration;

    @Value("${app.jwt.refresh-expiration}")
    private Duration refreshExpiration;

    @Autowired
    private ConversionService conversionService;

    @Override
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public boolean isValid(String jwtToken) {
        if (isExpired(jwtToken)) {
            return false;
        }
        return true;
    }

    @Override
    public String generate(UserToken token) {
        return buildToken(token, expiration.toMillis());
    }

    @Override
    public String generateRefreshToken(UserToken token) {
        return buildToken(token, refreshExpiration.toMillis());
    }

    private String buildToken(UserToken token, long expiration) {
        var now = System.currentTimeMillis();
        return Jwts.builder()
                .setClaims(getClaims(token))
                .setSubject(token.getUsername())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Map<String, Object> getClaims(UserToken token) {
        return Map.of(
                "firstName", token.getFirstName(),
                "lastName", token.getLastName()
        );
    }

    private boolean isExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String jwtToken) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }
}
