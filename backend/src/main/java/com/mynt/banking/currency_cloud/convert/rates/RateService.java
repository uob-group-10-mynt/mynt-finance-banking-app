package com.mynt.banking.currency_cloud.convert.rates;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.convert.rates.requests.*;
import com.mynt.banking.currency_cloud.manage.authenticate.AuthenticationService;
import com.mynt.banking.util.UriBuilderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RateService {

    private final AuthenticationService authenticationService;

    private final WebClient webClient;

    public Mono<ResponseEntity<JsonNode>> getDetailedRates(GetDetailedRatesRequest request) {
        String uri = UriBuilderUtil.buildUriWithQueryParams("/v2/rates/detailed", request);

        return webClient.get()
                .uri(uri)
                .header("X-Auth-Token", authenticationService.getAuthToken())
                .exchangeToMono(response -> response.toEntity(JsonNode.class))
                .flatMap(response -> {
                    if (response.getStatusCode().is2xxSuccessful()) {
                        JsonNode jsonNode = response.getBody();
                        ResponseEntity<JsonNode> newResponseEntity = new ResponseEntity<>(jsonNode, response.getStatusCode());
                        return Mono.just(newResponseEntity);
                    }
                    return Mono.just(response);
                });
    }

    public Mono<ResponseEntity<JsonNode>> getBasicRates(GetBasicRatesRequest request) {
        String uri = UriBuilderUtil.buildUriWithQueryParams("/v2/rates/find", request);
        return webClient
                .get()
                .uri(uri)
                .header("X-Auth-Token", authenticationService.getAuthToken())
                .exchangeToMono(response -> response.toEntity(JsonNode.class));
    }
}
