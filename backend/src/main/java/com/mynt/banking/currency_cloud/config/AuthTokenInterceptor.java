package com.mynt.banking.currency_cloud.config;

import com.mynt.banking.currency_cloud.manage.authenticate.AuthenticationService;
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
public class AuthTokenInterceptor implements ClientHttpRequestInterceptor {

    private final AuthenticationService authenticationService;

    public AuthTokenInterceptor(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @NotNull
    @Override
    public ClientHttpResponse intercept(@NotNull HttpRequest request, @NotNull byte[] body,
                                        @NotNull ClientHttpRequestExecution execution) throws IOException {

        try {
            if (authenticationService.getAuthToken() == null || authenticationService.isTokenExpired()) {
                authenticationService.refreshAuthToken();
            }

            String authToken = authenticationService.getAuthToken();
            request.getHeaders().set("X-Auth-Token", authToken);

            return execution.execute(request, body);
        } catch (Exception e) {
            log.error("Error occurred while processing authentication token: {}", e.getMessage());
            throw e;
        }
    }
}
