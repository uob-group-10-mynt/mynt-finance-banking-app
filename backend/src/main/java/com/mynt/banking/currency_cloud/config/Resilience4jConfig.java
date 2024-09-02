package com.mynt.banking.currency_cloud.config;

import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class Resilience4jConfig {

    @Bean
    public RetryConfig retryConfig() {
        return RetryConfig.custom()
                .ignoreExceptions(CallNotPermittedException.class)
                .build();
    }

    @Bean
    public RetryRegistry retryRegistry(RetryConfig retryConfig) {
        return RetryRegistry.of(retryConfig);
    }

    @Bean
    public RateLimiterConfig rateLimiterConfig() {
        return RateLimiterConfig.custom()
                .limitForPeriod(150)
                .timeoutDuration(Duration.ofMillis(1000))
                .limitRefreshPeriod(Duration.ofSeconds(60))
                .build();
    }

    @Bean
    public RateLimiterRegistry rateLimiterRegistry(RateLimiterConfig rateLimiterConfig) {
        return RateLimiterRegistry.of(rateLimiterConfig);
    }
}
