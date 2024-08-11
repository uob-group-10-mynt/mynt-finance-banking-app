package com.mynt.banking.currency_cloud.config;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RateLimitingInterceptor implements ClientHttpRequestInterceptor {

    private final RateLimiterRegistry rateLimiterRegistry;

    @NotNull
    @Override
    public ClientHttpResponse intercept(@NotNull HttpRequest request, @NotNull byte[] body,
                                        @NotNull ClientHttpRequestExecution execution) {

        // Identify the correct rate limiter based on the request path and authentication status
        RateLimiter rateLimiter;
        String requestPath = request.getURI().getPath();
        if (requestPath.equals("/v2/authenticate/api")) {
            rateLimiter = rateLimiterRegistry.rateLimiter("authenticate");
        } else if (requestPath.equals("/v2/rates/find") || requestPath.equals("/v2/rates/detailed")) {
            rateLimiter = rateLimiterRegistry.rateLimiter("rateRequests");
        } else if (isAuthenticatedRequest(request)) {
            rateLimiter = rateLimiterRegistry.rateLimiter("allOtherAuthenticated");
        } else {
            rateLimiter = rateLimiterRegistry.rateLimiter("allOtherUnauthenticated");
        }

        // Execute the request under the rate limiter
        try {
            return RateLimiter.decorateCheckedSupplier(rateLimiter, () -> execution.execute(request, body)).get();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isAuthenticatedRequest(HttpRequest request) {
        // Determine if the request is authenticated by checking for the presence of the "X-Auth-Token" header
        return request.getHeaders().containsKey("X-Auth-Token");
    }
}
