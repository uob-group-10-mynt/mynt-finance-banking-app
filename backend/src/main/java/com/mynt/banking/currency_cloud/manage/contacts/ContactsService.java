package com.mynt.banking.currency_cloud.manage.contacts;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.manage.authenticate.AuthenticationService;
import com.mynt.banking.util.UriBuilderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class ContactsService {

    private final AuthenticationService authenticationService;
    private final RestClient restClient;

    public ResponseEntity<JsonNode> createContact(CreateContactRequest requestBody) {
        return restClient
                .post()
                .uri("/v2/contacts/create")
                .body(requestBody)
                .retrieve()
                .toEntity(JsonNode.class);
    }

    public ResponseEntity<JsonNode> findContact(FindContactRequest requestBody) {
        return restClient
                .post()
                .uri("/v2/contacts/find")
                .body(requestBody)
                .retrieve()
                .toEntity(JsonNode.class);
    }

    public ResponseEntity<JsonNode> updateContact(String id, UpdateContactRequest request) {

        String uri = UriBuilderUtil.buildUriWithQueryParams("/v2/contacts/" + id, request);

        return restClient
                .post()
                .uri(uri)
                .body(request)
                .retrieve()
                .toEntity(JsonNode.class);
    }

    public ResponseEntity<JsonNode> getContact(String id) {
        String url = "/v2/contacts/" + id;
        return restClient
                .get()
                .uri(url)
                .retrieve()
                .toEntity(JsonNode.class);
    }
}
