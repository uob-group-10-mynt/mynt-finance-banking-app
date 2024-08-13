package com.mynt.banking.currency_cloud.collect.funding;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.manage.authenticate.AuthenticationService;
import com.mynt.banking.util.UriBuilderUtil;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FundingService {

    private final AuthenticationService authenticationService;

    private final RestClient restClient;

    public ResponseEntity<JsonNode> findAccountDetails(FindAccountDetailsRequest accountDetailsRequest) {
        // Build the URI with the provided parameters
        String uri = UriComponentsBuilder.fromPath("/v2/funding_accounts/find")
                .queryParam("currency", accountDetailsRequest.getCurrency())
                .queryParam("account_id", accountDetailsRequest.getAccountId())
                .queryParam("on_behalf_of", accountDetailsRequest.getOnBehalfOf())
                .queryParamIfPresent("payment_type", Optional.ofNullable(accountDetailsRequest.getPaymentType()))
                .toUriString();

        // Execute the GET request and retrieve the response
        return restClient.get()
                .uri(uri)
                .header("X-Auth-Token", authenticationService.getAuthToken())
                .retrieve()
                .toEntity(JsonNode.class);
    }

    public ResponseEntity<JsonNode> find(FindAccountDetailsRequest request) {
        String uri = UriBuilderUtil.buildUriWithQueryParams("/v2/funding_accounts/find", request);
        return restClient
                .get()
                .uri(uri)
                .retrieve()
                .toEntity(JsonNode.class);

    }
}
