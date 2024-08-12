package com.mynt.banking.currency_cloud.config;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class RateLimitingInterceptor implements ClientHttpRequestInterceptor {

    private final RateLimiterRegistry rateLimiterRegistry;

    @NotNull
    @Override
    public ClientHttpResponse intercept(@NotNull HttpRequest request, @NotNull byte[] body,
                                        @NotNull ClientHttpRequestExecution execution) throws IOException {

        RateLimiter rateLimiter = determineRateLimiter(request);

        try {
            return RateLimiter.decorateCheckedSupplier(rateLimiter, () -> execution.execute(request, body)).get();
        } catch (IOException e) {
            log.error("IO Error occurred while executing request: {}", e.getMessage());
            throw e;
        } catch (Throwable e) {
            log.error("Error occurred while applying rate limiting: {}", e.getMessage());
            throw new IOException("Rate limiting error", e);
        }
    }

    private RateLimiter determineRateLimiter(HttpRequest request) {
        String requestPath = request.getURI().getPath();
        if (requestPath.equals("/v2/authenticate/api")) {
            return rateLimiterRegistry.rateLimiter("authenticate");
        } else if (requestPath.equals("/v2/rates/find") || requestPath.equals("/v2/rates/detailed")) {
            return rateLimiterRegistry.rateLimiter("rateRequests");
        } else if (isAuthenticatedRequest(request)) {
            return rateLimiterRegistry.rateLimiter("allOtherAuthenticated");
        } else {
            return rateLimiterRegistry.rateLimiter("allOtherUnauthenticated");
        }
    }

    private boolean isAuthenticatedRequest(HttpRequest request) {
        return request.getHeaders().containsKey("X-Auth-Token");
    }
}
