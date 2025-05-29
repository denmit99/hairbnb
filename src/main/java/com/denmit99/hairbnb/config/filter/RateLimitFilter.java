package com.denmit99.hairbnb.config.filter;

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
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private static final String TOKENS_FIELD = "tokens";

    private static final String LAST_REFILL_FIELD = "lastRefill";

    private final RedisTemplate<String, String> redisTemplate;

    private final RateLimiterProperties rateLimiterProps;

    public RateLimitFilter(RedisTemplate<String, String> redisTemplate,
                           RateLimiterProperties rateLimiterProps) {
        this.redisTemplate = redisTemplate;
        this.rateLimiterProps = rateLimiterProps;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        long now = System.currentTimeMillis();
        String ip = getIp(request);

        var fields = redisTemplate.opsForHash().multiGet(ip, List.of(TOKENS_FIELD, LAST_REFILL_FIELD));
        long tokens = Optional.ofNullable((Long) fields.get(0)).orElse(rateLimiterProps.getBucketCapacity());
        long lastRefill = Optional.ofNullable((Long) fields.get(1)).orElse(now);
        long elapsedTime = now - lastRefill;
        long tokensToAdd = elapsedTime / rateLimiterProps.getRefillPeriod().toMillis()
                * rateLimiterProps.getRefillRate();

        // Only update lastRefill if tokens were actually added
        if (tokensToAdd > 0 && tokens < rateLimiterProps.getBucketCapacity()) {
            tokens = Math.min(rateLimiterProps.getBucketCapacity(), tokens + tokensToAdd);
            lastRefill = now;
        }

        if (tokens > 0) {
            tokens--;
        } else {
            long waitForRefill = (rateLimiterProps.getRefillPeriod().toMillis() - elapsedTime) / 1000;
            response.setHeader(Headers.X_RATE_LIMIT_RETRY_AFTER, String.valueOf(waitForRefill));
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        }
        redisTemplate.opsForHash().putAll(ip, Map.of(TOKENS_FIELD, tokens, LAST_REFILL_FIELD, lastRefill));
        response.setHeader(Headers.X_RATE_LIMIT_LIMIT, String.valueOf(rateLimiterProps.getBucketCapacity()));
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
