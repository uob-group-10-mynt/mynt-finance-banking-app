package com.mynt.banking.currency_cloud.pay.beneficiaries;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mynt.banking.client.pay.beneficiaries.BeneficiariesDetailResponse;
import com.mynt.banking.currency_cloud.config.WebClientErrorHandler;
import com.mynt.banking.currency_cloud.manage.authenticate.AuthenticationService;
import com.mynt.banking.currency_cloud.pay.beneficiaries.requests.CreateBeneficiaryRequest;
import com.mynt.banking.currency_cloud.pay.beneficiaries.requests.FindBeneficiaryRequest;
import com.mynt.banking.currency_cloud.pay.beneficiaries.requests.ValidateBeneficiaryRequest;
import io.swagger.v3.core.util.Json;
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
public class BeneficiaryService {

    private final AuthenticationService authenticationService;
    private final WebClient webClient;
    private final WebClientErrorHandler webClientErrorHandler;

    public ResponseEntity<JsonNode> get(String id, String onBehalfOf) {
        // Build the URI with the provided parameters
        String uri = UriComponentsBuilder.fromPath("/v2/beneficiaries/" + id)
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

    public ResponseEntity<JsonNode> find(FindBeneficiaryRequest requestBody) {
        return webClient
                .post()
                .uri("/v2/beneficiaries/find")
                .header("X-Auth-Token", authenticationService.getAuthToken())
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(HttpStatus.UNAUTHORIZED::equals, response -> webClientErrorHandler.handleUnauthorized("/v2/beneficiaries/find"))
                .onStatus(HttpStatusCode::is4xxClientError, webClientErrorHandler::handleClientError)
                .onStatus(HttpStatusCode::is5xxServerError, webClientErrorHandler::handleServerError)
                .toEntity(JsonNode.class)
                .block();
    }

    public Mono<ResponseEntity<JsonNode>> validate(ValidateBeneficiaryRequest requestBody) {
        return webClient
                .post()
                .uri("/v2/beneficiaries/validate")
                .header("X-Auth-Token", authenticationService.getAuthToken())
                .bodyValue(requestBody)
                .exchangeToMono(response -> response.toEntity(JsonNode.class))
                .flatMap(response -> {
                    if(response.getStatusCode().is2xxSuccessful()) {
                        return Mono.just(response);
                    }
                    return Mono.just(response);
                });

    }

    public Mono<ResponseEntity<JsonNode>> create(CreateBeneficiaryRequest requestBody) {
        return webClient
                .post()
                .uri("/v2/beneficiaries/create")
                .header("X-Auth-Token", authenticationService.getAuthToken())
                .bodyValue(requestBody)
                .exchangeToMono(response -> response.toEntity(JsonNode.class))
                .flatMap(response -> {
                    if(response.getStatusCode().is2xxSuccessful()) {
                        JsonNode jsonNode = response.getBody();
                        ObjectNode objectNode = ((ObjectNode) jsonNode);
                        ResponseEntity<JsonNode> newResponseEntity = new ResponseEntity<>(objectNode,response.getStatusCode());
                        return Mono.just(newResponseEntity);
                    }
                    return Mono.just(response);
                });
    }

}
