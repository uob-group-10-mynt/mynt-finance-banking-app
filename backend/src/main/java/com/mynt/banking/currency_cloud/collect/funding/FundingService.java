package com.mynt.banking.currency_cloud.collect.funding;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mynt.banking.currency_cloud.manage.authenticate.AuthenticationService;
import com.mynt.banking.util.HashMapToQuiryPrams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FundingService {

    private final AuthenticationService authenticationService;

    private final WebClient webClient;

    public ResponseEntity<JsonNode> findAccountDetails(FindAccountDetailsRequest accountDetailsRequest) {
        // Build the URI with the provided parameters
        String uri = UriComponentsBuilder.fromPath("/v2/funding_accounts/find")
                .queryParam("currency", accountDetailsRequest.getCurrency())
                .queryParam("account_id", accountDetailsRequest.getAccountId())
                .queryParam("on_behalf_of", accountDetailsRequest.getOnBehalfOf())
                .queryParamIfPresent("payment_type", Optional.ofNullable(accountDetailsRequest.getPaymentType()))
                .toUriString();

        // Execute the GET request and retrieve the response
        return webClient.get()
                .uri(uri)
                .header("X-Auth-Token", authenticationService.getAuthToken())
                .retrieve()
                .toEntity(JsonNode.class)
                .block();
    }





    public Mono<ResponseEntity<JsonNode>> find(FindAccountDetailsRequest request) {

        ObjectMapper objectMapper = new ObjectMapper();

        HashMap<String, Object> prams = objectMapper.convertValue(request, HashMap.class);
        String url = "/v2/funding_accounts/find" + HashMapToQuiryPrams.hashMapToString(prams);

        return webClient
                .get()
                .uri(url)
                .header("X-Auth-Token", authenticationService.getAuthToken())
                .exchangeToMono(response -> response.toEntity(JsonNode.class))
                .flatMap(response -> {
                    if(response.getStatusCode().is2xxSuccessful()) {
                        JsonNode jsonNode = response.getBody();
                        ObjectNode objectNode = ((ObjectNode) jsonNode).put("Custom Messsage","Hello World");
                        ResponseEntity<JsonNode> newResponseEntity = new ResponseEntity<>(objectNode,response.getStatusCode());
                        return Mono.just(newResponseEntity);
                    }
                    return Mono.just(response);
                });

    }
}
