package com.mynt.banking.currency_cloud.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mynt.banking.util.exceptions.currency_cloud.CurrencyCloudException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

// Service instead of Configuration because there should be trouble if there is only one client
// when there are loads of in coming requests?
@Service
@RequiredArgsConstructor
public class CurrencyCloudClient {
    private final CurrencyCloudAuthenticator currencyCloudAuthenticator;
    @Value("${currency.cloud.api.url}")
    private String apiUrl;

    public RestClient getClient() {
        return RestClient.builder()
                .baseUrl(apiUrl)
                .defaultStatusHandler(HttpStatus.UNAUTHORIZED::equals, this::handleUnauthorized)
                .defaultStatusHandler(HttpStatusCode::is4xxClientError, this::handleClientError)
                .defaultStatusHandler(HttpStatusCode::is5xxServerError, this::handleServerError)
                .build();
    }

    private void handleUnauthorized(HttpRequest request, ClientHttpResponse response) {
        this.currencyCloudAuthenticator.authenticate();
    }

    private void handleClientError(HttpRequest request, ClientHttpResponse response) throws CurrencyCloudException, IOException {
        Map<String,String> messageMap = new HashMap<>();
        messageMap.put("Client error", new String(response.getBody().readAllBytes()));
        messageMap.put("Request URI", request.getURI().toString());
        ObjectMapper objectMapper = new ObjectMapper();
        String message = objectMapper.writeValueAsString(messageMap);
        throw new CurrencyCloudException(message, response.getStatusCode());
    }

    private void handleServerError(HttpRequest request, ClientHttpResponse response) throws CurrencyCloudException, IOException {
        Map<String,String> messageMap = new HashMap<>();
        messageMap.put("Server error", new String(response.getBody().readAllBytes()));
        messageMap.put("Request URI", request.getURI().toString());
        ObjectMapper objectMapper = new ObjectMapper();
        String message = objectMapper.writeValueAsString(messageMap);
        throw new CurrencyCloudException(message, response.getStatusCode());
    }
}
