package com.mynt.banking.currency_cloud.pay.transfers;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final RestClient restClient;

    public ResponseEntity<JsonNode> createTransfer(CreateTransferRequest requestBody) {
        return restClient
                .post()
                .uri("/v2/transfers/create")
                .body(requestBody)
                .retrieve()
                .toEntity(JsonNode.class);
    }
}