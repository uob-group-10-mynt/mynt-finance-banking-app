//package com.mynt.banking.currency_cloud.config;
//
//import io.github.resilience4j.ratelimiter.RateLimiter;
//import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
//import io.github.resilience4j.retry.Retry;
//import io.github.resilience4j.retry.RetryRegistry;
//import io.github.resilience4j.ratelimiter.RequestNotPermitted;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.jetbrains.annotations.NotNull;
//import org.springframework.http.HttpRequest;
//import org.springframework.http.client.ClientHttpRequestExecution;
//import org.springframework.http.client.ClientHttpRequestInterceptor;
//import org.springframework.http.client.ClientHttpResponse;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.function.Supplier;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class RateLimitingAndRetryInterceptor implements ClientHttpRequestInterceptor {
//
//    private final RateLimiterRegistry rateLimiterRegistry;
//    private final RetryRegistry retryRegistry;
//
//    @NotNull
//    @Override
//    public ClientHttpResponse intercept(@NotNull HttpRequest request, @NotNull byte[] body,
//                                        @NotNull ClientHttpRequestExecution execution) throws IOException {
//        // Determine the correct rate limiter based on the request
//        RateLimiter rateLimiter = determineRateLimiter(request);
//        Retry retry = retryRegistry.retry("defaultRetry");
//
//        log.debug("Applying RateLimiter: {}", rateLimiter.getName());
//        log.debug("Applying Retry Policy: {}", retry.getName());
//
//        // Decorate the execution with rate limiter and retry
//        Supplier<ClientHttpResponse> responseSupplier = () -> {
//            try {
//                return execution.execute(request, body);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        };
//
//        Supplier<ClientHttpResponse> decoratedSupplier = RateLimiter.decorateSupplier(rateLimiter,
//                Retry.decorateSupplier(retry, responseSupplier));
//
//        try {
//            return decoratedSupplier.get();
//        } catch (RequestNotPermitted e) {
//            log.error("Rate limit exceeded for request: {}", request.getURI());
//            throw new IOException("Rate limit exceeded", e);
//        } catch (Exception e) {
//            log.error("Request failed after retries: {}", request.getURI());
//            throw new IOException("Request failed after retries", e);
//        }
//    }
//
//    private RateLimiter determineRateLimiter(HttpRequest request) {
//        String path = request.getURI().getPath();
//
//        if (path.contains("/v2/authenticate")) {
//            return rateLimiterRegistry.rateLimiter("authenticate");
//        } else if (path.contains("/v2/rates/find") || path.contains("/v2/rates/detailed")) {
//            return rateLimiterRegistry.rateLimiter("rateRequests");
//        } else if (request.getHeaders().containsKey("X-Auth-Token")) {
//            return rateLimiterRegistry.rateLimiter("allOtherAuthenticated");
//        } else {
//            return rateLimiterRegistry.rateLimiter("allOtherUnauthenticated");
//        }
//    }
//}
