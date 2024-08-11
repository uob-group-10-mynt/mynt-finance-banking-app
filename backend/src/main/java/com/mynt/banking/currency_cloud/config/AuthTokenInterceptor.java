package com.mynt.banking.currency_cloud.config;

import com.mynt.banking.currency_cloud.manage.authenticate.AuthenticationService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthTokenInterceptor implements ClientHttpRequestInterceptor {

    private final AuthenticationService authenticationService;

    public AuthTokenInterceptor(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @NotNull
    @Override
    public ClientHttpResponse intercept(@NotNull HttpRequest request, @NotNull byte[] body,
                                        @NotNull ClientHttpRequestExecution execution) throws IOException {

        // Check if the token is missing or needs to be refreshed
        if (authenticationService.getAuthToken() == null || authenticationService.isTokenExpired()) {
            authenticationService.refreshAuthToken();
        }

        // Get the current (and now possibly refreshed) authentication token
        String authToken = authenticationService.getAuthToken();

        // Add the authentication token to the request headers with 'X-Auth-Token' key
        request.getHeaders().set("X-Auth-Token", authToken);

        // Proceed with the request
        return execution.execute(request, body);
    }
}
