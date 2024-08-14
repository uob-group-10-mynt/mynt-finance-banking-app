package com.mynt.banking.currency_cloud.pay.payments;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.manage.authenticate.AuthenticationService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final RestClient restClient;

    public ResponseEntity<JsonNode> get(String id, String onBehalfOf) {
        // Build the URI with the provided parameters
        String uri = UriComponentsBuilder.fromPath("/v2/payments/" + id)
                .queryParam("on_behalf_of", onBehalfOf)
                .toUriString();

        // Execute the GET request and retrieve the response
        return restClient.get()
                .uri(uri)
                .retrieve()
                .toEntity(JsonNode.class);
    }

    public ResponseEntity<JsonNode> create(CreatePaymentRequest requestBody) {
        return restClient
                .post()
                .uri("/v2/payments/create")
                .body(requestBody)
                .retrieve()
                .toEntity(JsonNode.class);
    }
}
