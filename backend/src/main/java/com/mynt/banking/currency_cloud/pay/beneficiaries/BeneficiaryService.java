package com.mynt.banking.currency_cloud.pay.beneficiaries;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class BeneficiaryService {

    private final RestClient restClient;

    public ResponseEntity<JsonNode> get(String id, String onBehalfOf) {
        // Build the URI with the provided parameters
        String uri = UriComponentsBuilder.fromPath("/v2/beneficiaries/" + id)
                .queryParam("on_behalf_of", onBehalfOf)
                .toUriString();

        // Execute the GET request and retrieve the response
        return restClient.get()
                .uri(uri)
                .retrieve()
                .toEntity(JsonNode.class);
    }

    public ResponseEntity<JsonNode> find(FindBeneficiaryRequest requestBody) {
        return restClient
                .post()
                .uri("/v2/beneficiaries/find")
                .body(requestBody)
                .retrieve()
                .toEntity(JsonNode.class);
    }

    public ResponseEntity<JsonNode> validate(ValidateBeneficiaryRequest requestBody) {
        return restClient
                .post()
                .uri("/v2/beneficiaries/validate")
                .body(requestBody)
                .retrieve()
                .toEntity(JsonNode.class);
    }

    public ResponseEntity<JsonNode> create(CreateBeneficiaryRequest requestBody) {
        return restClient
                .post()
                .uri("/v2/beneficiaries/create")
                .body(requestBody)
                .retrieve()
                .toEntity(JsonNode.class);
    }

    public ResponseEntity<JsonNode> delete(String id, String onBehalfOf) {
        // Build the URI with the provided parameters
        String uri = UriComponentsBuilder.fromPath("/v2/beneficiaries/" + id + "/delete").toUriString();

        // Create the form data inline
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("on_behalf_of", onBehalfOf);

        return restClient.post()
                .uri(uri)
                .contentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED)
                .body(formData)
                .retrieve()
                .toEntity(JsonNode.class);
    }
}
