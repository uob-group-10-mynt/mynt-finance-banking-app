package com.mynt.banking.currency_cloud.manage.transactions;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.config.FallbackConfig;
import com.mynt.banking.util.UriBuilderUtil;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TransactionService {

    private final RestClient restClient;

    public ResponseEntity<JsonNode> find(
            String currencyCode,
            String relatedEntityType,
            String onBehalfOf,
            LocalDate createdAtFrom,
            LocalDate createdAtTo,
            Integer perPage,
            Integer page) {

        // Form uri for get request:
        String uri = String.valueOf(UriComponentsBuilder.fromPath("/v2/transactions/find")
                .queryParamIfPresent("on_behalf_of", Optional.of(onBehalfOf))
                .queryParamIfPresent("currency", Optional.ofNullable(currencyCode))
                .queryParamIfPresent("related_entity_type", Optional.ofNullable(relatedEntityType))
                .queryParamIfPresent("created_at_from", Optional.ofNullable(createdAtFrom))
                .queryParamIfPresent("created_at_to", Optional.ofNullable(createdAtTo))
                .queryParamIfPresent("per_page", Optional.ofNullable(perPage))
                .queryParamIfPresent("page", Optional.ofNullable(page))
                .queryParamIfPresent("order_asc_desc", Optional.of("desc"))
                .toUriString());

        // Execute the GET request and retrieve the response
        return restClient.get()
                .uri(uri)
                .retrieve()
                .toEntity(JsonNode.class);
    }

    public ResponseEntity<JsonNode> get(String transactionId, String onBehalfOf) {
        // Initialize UriComponentsBuilder
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath("/v2/transactions/" + transactionId);

        // Add query parameters only if they are non-null
        uriBuilder.queryParam("on_behalf_of", onBehalfOf);
        String uri = uriBuilder.toUriString();

        // Execute the GET request and retrieve the response
        return restClient.get()
                .uri(uri)
                .retrieve()
                .toEntity(JsonNode.class);
    }

    public ResponseEntity<JsonNode> find(FindTransactionRequest requestBody) {
        String uri = UriBuilderUtil.buildUriWithQueryParams("/v2/transactions/find", requestBody);
        return restClient
                .get()
                .uri(uri)
                .retrieve()
                .toEntity(JsonNode.class);
    }

    public ResponseEntity<JsonNode> findTransactionID(String id, String onBehalfOfId) {
        String url = "/v2/transactions/"+id;
        if(onBehalfOfId != null && !onBehalfOfId.isEmpty()) { url = url + "?on_behalf_of=" + onBehalfOfId; }
        return restClient
                .get()
                .uri(url)
                .retrieve()
                .toEntity(JsonNode.class);
    }
}
