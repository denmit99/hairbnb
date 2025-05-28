package com.denmit99.hairbnb.config;

import com.denmit99.hairbnb.constants.Headers;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    //TODO move to properties
    private static final int BUCKET_CAPACITY = 5;

    private static final Duration REFILL_RATE = Duration.ofMinutes(1);

    private static final String TOKENS_FIELD = "tokens";

    private static final String LAST_REFILL_FIELD = "lastRefill";

    private final RedisTemplate<String, String> redisTemplate;

    public RateLimitFilter(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        long now = System.currentTimeMillis();
        String ip = getIp(request);

        var fields = redisTemplate.opsForHash().multiGet(ip, List.of(TOKENS_FIELD, LAST_REFILL_FIELD));
        int tokens = Optional.ofNullable((Integer) fields.get(0)).orElse(BUCKET_CAPACITY);
        long lastRefill = Optional.ofNullable((Long) fields.get(1)).orElse(now);
        long sinceLastRefill = now - lastRefill;

        if (sinceLastRefill > REFILL_RATE.toMillis() && tokens < BUCKET_CAPACITY) {
            tokens = BUCKET_CAPACITY;
            lastRefill = now;
        }

        if (tokens > 0) {
            tokens--;
        } else {
            long waitForRefill = (REFILL_RATE.toMillis() - sinceLastRefill) / TimeUnit.SECONDS.toMillis(1);
            response.setHeader(Headers.X_RATE_LIMIT_RETRY_AFTER, String.valueOf(waitForRefill));
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        }
        redisTemplate.opsForHash().putAll(ip, Map.of(TOKENS_FIELD, tokens, LAST_REFILL_FIELD, lastRefill));
        response.setHeader(Headers.X_RATE_LIMIT_LIMIT, String.valueOf(BUCKET_CAPACITY));
        response.setHeader(Headers.X_RATE_LIMIT_REMAINING, String.valueOf(tokens));

        filterChain.doFilter(request, response);
    }

    private static String getIp(HttpServletRequest request) {
        String ip = request.getHeader(Headers.X_FORWARDED_FOR);
        if (ip == null || ip.isBlank()) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
