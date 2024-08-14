package com.mynt.banking.currency_cloud.collect.demo;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class DemoService {

    private final RestClient restClient;

    public ResponseEntity<JsonNode> create(DemoFundingDto requestBody) {
        return restClient
                .post()
                .uri("/v2/demo/funding/create")
                .body(requestBody)
                .retrieve()
                .toEntity(JsonNode.class);
    }
}
