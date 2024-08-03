package com.mynt.banking.currency_cloud.manage.transactions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mynt.banking.currency_cloud.manage.authenticate.AuthenticationService;
import com.mynt.banking.currency_cloud.manage.contacts.requestsDtos.CreateContact;
import com.mynt.banking.currency_cloud.manage.transactions.requests.FindTransaction;
import com.mynt.banking.util.HashMapToQuiryPrams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.HashMap;

@RequiredArgsConstructor
@Service
public class TransactionService {

    private final AuthenticationService authenticationService;
    private final WebClient webClient;

    public ResponseEntity<JsonNode> find(
            String currencyCode,
            String relatedEntityType,
            String onBehalfOf,
            Integer perPage,
            Integer page) {

        // Initialize UriComponentsBuilder
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath("/v2/transactions/find");

        // Add query parameters only if they are non-null
        if (onBehalfOf != null)  uriBuilder.queryParam("on_behalf_of", onBehalfOf);
        if (currencyCode != null) uriBuilder.queryParam("currency", currencyCode);
        if (relatedEntityType != null) uriBuilder.queryParam("related_entity_type", relatedEntityType);
        if (perPage != null) uriBuilder.queryParam("per_page", perPage);
        if (page != null) uriBuilder.queryParam("page", page);

        String uri = uriBuilder.toUriString();

        // Execute the GET request and retrieve the response
        return webClient.get()
                .uri(uri)
                .header("X-Auth-Token", authenticationService.getAuthToken())
                .retrieve()
                .toEntity(JsonNode.class)
                .block();
    }



    public Mono<ResponseEntity<JsonNode>> find(FindTransaction requestBody) {

        ObjectMapper objectMapper = new ObjectMapper();

        HashMap<String, Object> prams = objectMapper.convertValue(requestBody, HashMap.class);
        String url = "/v2/transactions/find" + HashMapToQuiryPrams.hashMapToString(prams);

        return webClient
                .get()
                .uri(url)
                .header("X-Auth-Token", authenticationService.getAuthToken())
                .exchangeToMono(response -> response.toEntity(JsonNode.class))
                .flatMap(response -> Mono.just(response) );
    }

    public Mono<ResponseEntity<JsonNode>> findTransactionID(String id, String onBehalfOfId) {

        String url = "/v2/transactions/"+id;

        if(onBehalfOfId != null && !onBehalfOfId.isEmpty()) {
            url = url + "?on_behalf_of=" + onBehalfOfId;
        }

        return webClient
                .get()
                .uri(url)
                .header("X-Auth-Token", authenticationService.getAuthToken())
                .exchangeToMono(response -> response.toEntity(JsonNode.class))
                .flatMap(response -> Mono.just(response) );
    }


}
