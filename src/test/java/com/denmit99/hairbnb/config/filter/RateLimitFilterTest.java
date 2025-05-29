package com.denmit99.hairbnb.config.filter;

import com.denmit99.hairbnb.constants.Headers;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RateLimitFilterTest {
    private static final long DEFAULT_BUCKET_SIZE = 10L;

    @Mock
    private HashOperations hashOperations;

    @Mock
    private RateLimiterProperties rateLimiterProps;

    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @InjectMocks
    private RateLimitFilter underTest;

    @BeforeEach
    public void setUp() {
        when(rateLimiterProps.getBucketCapacity()).thenReturn(DEFAULT_BUCKET_SIZE);
        when(rateLimiterProps.getRefillRate()).thenReturn(DEFAULT_BUCKET_SIZE);
        when(rateLimiterProps.getRefillPeriod()).thenReturn(Duration.ofSeconds(10));
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
    }

    @Test
    public void doFilterInternal_RedisValuesMissing_Ok() throws ServletException, IOException {
        long bucketSize = rateLimiterProps.getBucketCapacity();
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
        when(hashOperations.multiGet(any(), any())).thenReturn(Arrays.asList(null, null));
        var response = mock(HttpServletResponse.class);
        var mapArgumentCaptor = ArgumentCaptor.forClass(Map.class);

        underTest.doFilterInternal(mock(HttpServletRequest.class), response, mock(FilterChain.class));

        verify(redisTemplate.opsForHash(), times(1)).putAll(any(), mapArgumentCaptor.capture());
        assertEquals(bucketSize - 1, mapArgumentCaptor.getValue().get("tokens"));
        verify(response).setHeader(Headers.X_RATE_LIMIT_LIMIT, String.valueOf(bucketSize));
        verify(response).setHeader(Headers.X_RATE_LIMIT_REMAINING, String.valueOf(bucketSize - 1));
    }

    @Test
    public void doFilterInternal_NoTokensLeft_SetStatus429() throws ServletException, IOException {
        when(hashOperations.multiGet(any(), any())).thenReturn(Arrays.asList(0L, System.currentTimeMillis()));
        var response = mock(HttpServletResponse.class);

        underTest.doFilterInternal(mock(HttpServletRequest.class), response, mock(FilterChain.class));

        verify(response).setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        verify(response).setHeader(eq(Headers.X_RATE_LIMIT_RETRY_AFTER), any());
        verify(response).setHeader(Headers.X_RATE_LIMIT_LIMIT, String.valueOf(rateLimiterProps.getBucketCapacity()));
        verify(response).setHeader(Headers.X_RATE_LIMIT_REMAINING, String.valueOf(0L));
    }

    @Test
    public void doFilterInternal_TokensAvailable_Ok() throws ServletException, IOException {
        long bucketsLeft = 4L;
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
        when(hashOperations.multiGet(any(), any())).thenReturn(Arrays.asList(bucketsLeft, System.currentTimeMillis()));
        var response = mock(HttpServletResponse.class);
        var mapArgumentCaptor = ArgumentCaptor.forClass(Map.class);

        underTest.doFilterInternal(mock(HttpServletRequest.class), response, mock(FilterChain.class));

        verify(redisTemplate.opsForHash(), times(1)).putAll(any(), mapArgumentCaptor.capture());
        assertEquals(bucketsLeft - 1, mapArgumentCaptor.getValue().get("tokens"));
        verify(response).setHeader(Headers.X_RATE_LIMIT_LIMIT, String.valueOf(rateLimiterProps.getBucketCapacity()));
        verify(response).setHeader(Headers.X_RATE_LIMIT_REMAINING, String.valueOf(bucketsLeft - 1));
    }
}
