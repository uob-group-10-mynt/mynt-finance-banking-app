package com.mynt.banking.currency_cloud.convert.conversions;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.convert.conversions.requests.CreateConversionRequest;
import com.mynt.banking.currency_cloud.manage.accounts.requests.CreateAccountRequest;
import com.mynt.banking.currency_cloud.manage.authenticate.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ConversionService {

    private final AuthenticationService authenticationService;

    private final WebClient webClient;

    public Mono<ResponseEntity<JsonNode>> createConversion(CreateConversionRequest requestBody) {
        return webClient
                .post()
                .uri("/v2/conversions/create")
                .header("X-Auth-Token", authenticationService.getAuthToken())
                .bodyValue(requestBody)
                .exchangeToMono(response -> response.toEntity(JsonNode.class));
    }
}
