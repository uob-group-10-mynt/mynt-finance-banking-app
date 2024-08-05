package com.mynt.banking.currency_cloud.manage.transactions;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.config.WebClientErrorHandler;
import com.mynt.banking.currency_cloud.manage.authenticate.AuthenticationService;
import com.mynt.banking.currency_cloud.manage.transactions.requests.FindTransaction;
import com.mynt.banking.util.UriBuilderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TransactionService {

    private final AuthenticationService authenticationService;
    private final WebClient webClient;
    private final WebClientErrorHandler webClientErrorHandler;

    public ResponseEntity<JsonNode> find(
            String currencyCode,
            String relatedEntityType,
            String onBehalfOf,
            Integer perPage,
            Integer page) {

        // Form uri for get request:
        String uri = UriComponentsBuilder.fromPath("/v2/transactions/find")
                .queryParamIfPresent("on_behalf_of", Optional.of(onBehalfOf))
                .queryParamIfPresent("currency", Optional.ofNullable(currencyCode))
                .queryParamIfPresent("related_entity_type", Optional.ofNullable(relatedEntityType))
                .queryParamIfPresent("per_page", Optional.ofNullable(perPage))
                .queryParamIfPresent("page", Optional.ofNullable(page)).toUriString();

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

    public ResponseEntity<JsonNode> get(String transactionId, String onBehalfOf) {
        // Initialize UriComponentsBuilder
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath("/v2/transactions/" + transactionId);

        // Add query parameters only if they are non-null
        uriBuilder.queryParam("on_behalf_of", onBehalfOf);
        String uri = uriBuilder.toUriString();

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

    public Mono<ResponseEntity<JsonNode>> find(FindTransaction requestBody) {
        String uri = UriBuilderUtil.buildUriWithQueryParams("/v2/transactions/find", requestBody);
        return webClient
                .get()
                .uri(uri)
                .header("X-Auth-Token", authenticationService.getAuthToken())
                .exchangeToMono(response -> response.toEntity(JsonNode.class))
                .flatMap(Mono::just);
    }

    public Mono<ResponseEntity<JsonNode>> findTransactionID(String id, String onBehalfOfId) {
        String url = "/v2/transactions/"+id;
        if(onBehalfOfId != null && !onBehalfOfId.isEmpty()) { url = url + "?on_behalf_of=" + onBehalfOfId; }
        return webClient
                .get()
                .uri(url)
                .header("X-Auth-Token", authenticationService.getAuthToken())
                .exchangeToMono(response -> response.toEntity(JsonNode.class))
                .flatMap(Mono::just);
    }
}
