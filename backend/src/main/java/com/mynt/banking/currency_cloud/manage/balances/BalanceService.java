package com.mynt.banking.currency_cloud.manage.balances;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.manage.authenticate.AuthenticationService;
import com.mynt.banking.util.UriBuilderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Service
public class BalanceService {

    private final AuthenticationService authenticationService;
    private final RestClient restClient;

    public ResponseEntity<JsonNode> get(String currencyCode, String onBehalfOf) {
        // Build the URI with the provided parameters
        String uri = UriComponentsBuilder.fromPath("/v2/balances/" + currencyCode)
                .queryParam("on_behalf_of", onBehalfOf)
                .toUriString();

        // Execute the GET request and retrieve the response
        return restClient.get()
                .uri(uri)
                .retrieve()
                .toEntity(JsonNode.class);
    }

    public ResponseEntity<JsonNode> find(String onBehalfOf) {
        // Build the URI with the provided parameters
        String uri = UriComponentsBuilder.fromPath("/v2/balances/find")
                .queryParam("on_behalf_of", onBehalfOf)
                .toUriString();

        // Execute the GET request and retrieve the response
        return restClient.get()
                .uri(uri)
                .header("X-Auth-Token", authenticationService.getAuthToken())
                .retrieve()
                .toEntity(JsonNode.class);
    }

    public ResponseEntity<JsonNode> find(FindBalanceRequest request) {
        String uri = UriBuilderUtil.buildUriWithQueryParams("/v2/balances/find", request);
        return restClient
                .get()
                .uri(uri)
                .retrieve()
                .toEntity(JsonNode.class);

    }

    public ResponseEntity<JsonNode> findForParticularCurrency(FindBalancesRequest request, String currencyCode) {
        String uri = UriBuilderUtil.buildUriWithQueryParams("/v2/balances/" + currencyCode, request);
        return restClient
                .get()
                .uri(uri)
                .retrieve()
                .toEntity(JsonNode.class);
    }

    public ResponseEntity<JsonNode> getBalance(String currency, GetBalanceRequest request) {
        String uri = UriBuilderUtil.buildUriWithQueryParams("/v2/balances/" + currency, request);
        return restClient
                .get()
                .uri(uri)
                .retrieve()
                .toEntity(JsonNode.class);
    }
}
