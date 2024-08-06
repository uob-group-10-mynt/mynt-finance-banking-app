package com.mynt.banking.currency_cloud.convert.conversions;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.config.WebClientErrorHandler;
import com.mynt.banking.currency_cloud.convert.conversions.requests.CreateConversionRequest;
import com.mynt.banking.currency_cloud.manage.accounts.requests.CreateAccountRequest;
import com.mynt.banking.currency_cloud.manage.authenticate.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ConversionService {

    private final AuthenticationService authenticationService;
    private final WebClient webClient;
    private final WebClientErrorHandler webClientErrorHandler;

    public ResponseEntity<JsonNode> get(String id, String onBehalfOf) {
        // Build the URI with the provided parameters
        String uri = UriComponentsBuilder.fromPath("/v2/conversions/" + id)
                .queryParam("on_behalf_of", onBehalfOf)
                .toUriString();

        // Execute the GET request and retrieve the response
        return webClient.get()
                .uri(uri)
                .header("X-Auth-Token", authenticationService.getAuthToken())
                .retrieve()
                .onStatus(HttpStatus.UNAUTHORIZED::equals, response -> webClientErrorHandler.handleUnauthorized(uri))
                .onStatus(HttpStatusCode::is4xxClientError, webClientErrorHandler::handleClientError)
                .onStatus(HttpStatusCode::is5xxServerError, webClientErrorHandler::handleServerError)
                .toEntity(JsonNode.class)
                .block();
    }

    public Mono<ResponseEntity<JsonNode>> createConversion(CreateConversionRequest requestBody) {
        return webClient
                .post()
                .uri("/v2/conversions/create")
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
