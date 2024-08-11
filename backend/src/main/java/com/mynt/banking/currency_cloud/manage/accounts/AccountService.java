package com.mynt.banking.currency_cloud.manage.accounts;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final RestClient restClient;

    public ResponseEntity<JsonNode> get(String accountID, String onBehalfOf) {
        String uri = UriComponentsBuilder.fromPath("/v2/accounts/" + accountID)
                .queryParam("on_behalf_of", onBehalfOf)
                .toUriString();

        return restClient.get()
                .uri(uri)
                .retrieve()
                .toEntity(JsonNode.class);
    }

    public ResponseEntity<JsonNode> create(CreateAccountRequest requestBody) {
        return restClient
                .post()
                .uri("/v2/accounts/create")
                .body(requestBody)
                .retrieve()
                .toEntity(JsonNode.class);
    }

    public ResponseEntity<JsonNode> find(FindAccountRequest requestBody) {
        return restClient
                .post()
                .uri("/v2/accounts/find")
                .body(requestBody)
                .retrieve()
                .toEntity(JsonNode.class);
    }

    public ResponseEntity<JsonNode> update(UpdateAccountRequest request, String id) {

        String url = "/v2/accounts/" + id;

        return restClient
                .post()
                .uri(url)
                .body(request)
                .retrieve()
                .toEntity(JsonNode.class);
    }
}
