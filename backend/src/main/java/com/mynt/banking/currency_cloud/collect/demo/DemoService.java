package com.mynt.banking.currency_cloud.collect.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.collect.demo.requests.DemoFundingDto;
import com.mynt.banking.currency_cloud.convert.conversions.requests.CreateConversionRequest;
import com.mynt.banking.currency_cloud.manage.authenticate.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DemoService {

    private final AuthenticationService authenticationService;

    private final WebClient webClient;

    public Mono<ResponseEntity<JsonNode>> create(DemoFundingDto requestBody) {
        return webClient
                .post()
                .uri("/v2/demo/funding/create")
                .header("X-Auth-Token", authenticationService.getAuthToken())
                .bodyValue(requestBody)
                .exchangeToMono(response -> response.toEntity(JsonNode.class))
                .flatMap(response -> {
                    if(response.getStatusCode().is2xxSuccessful()) {
                        JsonNode jsonNode = response.getBody();
                        ResponseEntity<JsonNode> newResponseEntity = new ResponseEntity<>(jsonNode, response.getStatusCode());
                        return Mono.just(newResponseEntity);
                    }
                    return Mono.just(response);
                });
    }


}
