//package com.mynt.banking.currency_cloud.config;
//
//import io.github.resilience4j.ratelimiter.RateLimiter;
//import io.github.resilience4j.ratelimiter.RateLimiterConfig;
//import io.github.resilience4j.retry.Retry;
//import io.github.resilience4j.retry.RetryConfig;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.time.Duration;
//
//@Configuration
//public class Resilience4jConfig {
//
////    @Bean
////    public RateLimiter authenticatedRequestsRateLimiter() {
////        RateLimiterConfig config = RateLimiterConfig.custom()
////                .limitForPeriod(150)  // Maximum 150 requests per minute
////                .limitRefreshPeriod(Duration.ofMinutes(1))
////                .timeoutDuration(Duration.ofSeconds(1))  // Wait up to 1 second if limit is exceeded
////                .build();
////        return RateLimiter.of("currencyCloudRequests", config);
////    }
//
//    @Bean
//    public RateLimiter authenticatedRequestsRateLimiter() {
//        RateLimiterConfig config = RateLimiterConfig.custom()
//                .limitForPeriod(5)  // Maximum 150 requests per minute
//                .limitRefreshPeriod(Duration.ofSeconds(1))
//                .timeoutDuration(Duration.ofMillis(500))  // Wait up to 1 second if limit is exceeded
//                .build();
//        return RateLimiter.of("currencyCloudRequests", config);
//    }
//
//    @Bean
//    public Retry defaultRetry() {
//        RetryConfig config = RetryConfig.custom()
//                .maxAttempts(3)  // Retry up to 3 times
//                .waitDuration(Duration.ofSeconds(60))  // Wait 60 seconds before retrying
//                .build();
//        return Retry.of("defaultRetry", config);
//    }
//}
