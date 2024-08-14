package com.mynt.banking.currency_cloud.config;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Slf4j
@Component
public class LoggingInterceptor implements ClientHttpRequestInterceptor {

    @NotNull
    @Override
    public ClientHttpResponse intercept(@NotNull HttpRequest request, @NotNull byte[] body,
                                        @NotNull ClientHttpRequestExecution execution) throws IOException {

        logRequestDetails(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        logResponseDetails(response);
        return response;
    }

    private void logRequestDetails(HttpRequest request, byte[] body) throws IOException {
        log.info("Request Method: {}", request.getMethod());
        log.info("Request URI: {}", request.getURI());
        log.info("Request Headers: {}", request.getHeaders());
        log.info("Request Body: {}", new String(body, StandardCharsets.UTF_8));
    }

    private void logResponseDetails(ClientHttpResponse response) throws IOException {
        StringBuilder inputStringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), StandardCharsets.UTF_8))) {
            String line = bufferedReader.lines().collect(Collectors.joining("\n"));
            inputStringBuilder.append(line);
        }

        log.info("Response Status Code: {}", response.getStatusCode());
        log.info("Response Headers: {}", response.getHeaders());
        log.info("Response Body: {}", inputStringBuilder);
    }
}

