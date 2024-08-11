package com.mynt.banking.currency_cloud.convert.rates;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.util.UriBuilderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class RateService {

    private final RestClient restClient;

    public ResponseEntity<JsonNode> getDetailedRates(GetDetailedRatesRequest request) {
        String uri = UriBuilderUtil.buildUriWithQueryParams("/v2/rates/detailed", request);
        return restClient.get()
                .uri(uri)
                .retrieve()
                .toEntity(JsonNode.class);
    }

    public ResponseEntity<JsonNode> getBasicRates(GetBasicRatesRequest request) {
        String uri = UriBuilderUtil.buildUriWithQueryParams("/v2/rates/find", request);
        return restClient
                .get()
                .uri(uri)
                .retrieve()
                .toEntity(JsonNode.class);
    }
}
