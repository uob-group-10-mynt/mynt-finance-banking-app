package com.mynt.banking.currency_cloud.manage.balances;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mynt.banking.currency_cloud.config.WebClientErrorHandler;
import com.mynt.banking.currency_cloud.manage.authenticate.AuthenticationService;
import com.mynt.banking.util.HashMapToQuiryPrams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@RequiredArgsConstructor
@Service
public class BalanceService {

    private final AuthenticationService authenticationService;
    private final WebClient webClient;
    private final WebClientErrorHandler webClientErrorHandler;

    public ResponseEntity<JsonNode> find(String currencyCode, String onBehalfOf) {
        // Build the URI with the provided parameters
        String uri = UriComponentsBuilder.fromPath("/v2/balances/" + currencyCode)
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

    public ResponseEntity<JsonNode> find(String onBehalfOf) {
        // Build the URI with the provided parameters
        String uri = UriComponentsBuilder.fromPath("/v2/balances/find")
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


    public Mono<ResponseEntity<JsonNode>> find(FindBalanceRequest request) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> prams = objectMapper.convertValue(request, HashMap.class);
        String url = "/v2/balances/find" + HashMapToQuiryPrams.hashMapToString(prams);

        return webClient
                .get()
                .uri(url)
                .header("X-Auth-Token", authenticationService.getAuthToken())
                .exchangeToMono(response -> response.toEntity(JsonNode.class))
                .flatMap(response -> {
                    if(response.getStatusCode().is2xxSuccessful()) {
                        JsonNode jsonNode = response.getBody();
                        ObjectNode objectNode = ((ObjectNode) jsonNode).put("Custom Messsage","Hello World");
                        ResponseEntity<JsonNode> newResponseEntity = new ResponseEntity<>(objectNode,response.getStatusCode());
                        return Mono.just(newResponseEntity);
                    }
                    return Mono.just(response);
                });

    }

    public Mono<ResponseEntity<JsonNode>> findForParticularCurrency(FindBalancesRequest request, String currencyCode) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> prams = objectMapper.convertValue(request, HashMap.class);
        String url = "/v2/balances/"+currencyCode;//+"/";//+ HashMapToQuiryPrams.hashMapToString(prams);

        return webClient
                .get()
                .uri(url)
                .header("X-Auth-Token", authenticationService.getAuthToken())
                .exchangeToMono(response -> response.toEntity(JsonNode.class))
                .flatMap(response -> {
                    if(response.getStatusCode().is2xxSuccessful()) {
                        JsonNode jsonNode = response.getBody();
                        ObjectNode objectNode = ((ObjectNode) jsonNode).put("Custom Messsage","Hello World");
                        ResponseEntity<JsonNode> newResponseEntity = new ResponseEntity<>(objectNode,response.getStatusCode());
                        return Mono.just(newResponseEntity);
                    }
                    return Mono.just(response);
                });
    }
}
