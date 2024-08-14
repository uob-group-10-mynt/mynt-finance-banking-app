package com.mynt.banking.currency_cloud.config;

import com.mynt.banking.currency_cloud.manage.authenticate.AuthenticationService;
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
public class AuthTokenInterceptor implements ClientHttpRequestInterceptor {

    private final AuthenticationService authenticationService;

    @NotNull
    @Override
    public ClientHttpResponse intercept(@NotNull HttpRequest request, @NotNull byte[] body,
                                        @NotNull ClientHttpRequestExecution execution) throws IOException {

        try {
            // Check if the token is null or expired
            if (authenticationService.getAuthToken() == null || authenticationService.isTokenExpired()) {
                log.info("Token is null or expired, refreshing token.");
                authenticationService.refreshAuthToken();
            }

            String authToken = authenticationService.getAuthToken();
            if (authToken != null) {
                request.getHeaders().set("X-Auth-Token", authToken);
            } else {
                log.warn("Auth token is still null after attempting refresh.");
            }

            // Proceed with the request
            return execution.execute(request, body);

        } catch (Exception e) {
            log.error("Error occurred while processing authentication token: {}", e.getMessage());
            throw e;
        }
    }
}
