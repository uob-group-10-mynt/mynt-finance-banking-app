package com.mynt.banking.currency_cloud.convert.rates;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.convert.rates.requests.GetDetailedRatesRequest;
import com.mynt.banking.currency_cloud.manage.authenticate.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RateService {

    private final AuthenticationService authenticationService;

    private final WebClient webClient;

    public Mono<ResponseEntity<JsonNode>> getDetailedRates(GetDetailedRatesRequest request) {
        String uri = UriComponentsBuilder.fromPath("/v2/rates/detailed")
                .queryParam("buy_currency", request.getBuyCurrency())
                .queryParam("sell_currency", request.getSellCurrency())
                .queryParam("fixed_side", request.getFixedSide())
                .queryParam("amount", request.getAmount())
                .queryParam("on_behalf_of", request.getOnBehalfOf())
                .queryParam("conversion_date", request.getConversionDate())
                .queryParam("conversion_date_preference", request.getConversionDatePreference())
                .build()
                .toUriString();

        return webClient.get()
                .uri(uri)
                .header("X-Auth-Token", authenticationService.getAuthToken())
                .exchangeToMono(response -> response.toEntity(JsonNode.class))
                .flatMap(response -> {
                    if (response.getStatusCode().is2xxSuccessful()) {
                        JsonNode jsonNode = response.getBody();
                        ResponseEntity<JsonNode> newResponseEntity = new ResponseEntity<>(jsonNode, response.getStatusCode());
                        return Mono.just(newResponseEntity);
                    }
                    return Mono.just(response);
                });
    }
}
