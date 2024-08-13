package com.mynt.banking.currency_cloud.manage.reference;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.manage.authenticate.AuthenticationService;
import com.mynt.banking.util.UriBuilderUtil;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;


@RequiredArgsConstructor
@Service
public class ReferenceService {

    private final RestClient restClient;

    public ResponseEntity<JsonNode> getPayerRequirements(GetPayerRequirementsRequest request) {
        String uri = UriBuilderUtil.buildUriWithQueryParams("/v2/reference/payer_required_details", request);

        return restClient
                .get()
                .uri(uri)
                .retrieve()
                .toEntity(JsonNode.class);
    }

    public ResponseEntity<JsonNode> getBeneficiaryRequirements(GetBeneficiaryRequirementsRequest request) {

        String uri = UriBuilderUtil.buildUriWithQueryParams("/v2/reference/beneficiary_required_details", request);
        return restClient
                .get()
                .uri(uri)
                .retrieve()
                .toEntity(JsonNode.class);
    }

    public ResponseEntity<JsonNode> getAvailableCurrencies() {
        return restClient
                .get()
                .uri("/v2/reference/currencies")
                .retrieve()
                .toEntity(JsonNode.class);
    }
}
