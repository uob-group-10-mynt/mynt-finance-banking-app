package com.mynt.banking.currency_cloud.manage.balances;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mynt.banking.currency_cloud.manage.authenticate.AuthenticationService;
import com.mynt.banking.currency_cloud.manage.balances.requests.FindBalanceAllCurrencies;
import com.mynt.banking.currency_cloud.manage.balances.requests.FindBalancesRequest;
import com.mynt.banking.util.HashMapToQuiryPrams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@RequiredArgsConstructor
@Service
public class BalanceService {

    private final AuthenticationService authenticationService;
    private final WebClient webClient;

    public Mono<ResponseEntity<JsonNode>> find(FindBalanceAllCurrencies request) {

        ObjectMapper objectMapper = new ObjectMapper();

        HashMap<String, Object> prams = objectMapper.convertValue(request, HashMap.class);
        String url = "/v2/balances/find" + HashMapToQuiryPrams.hashMapToString(prams);

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

    public Mono<ResponseEntity<JsonNode>> findForParticularCurrency(FindBalancesRequest request, String currencyCode) {

        ObjectMapper objectMapper = new ObjectMapper();

        HashMap<String, Object> prams = objectMapper.convertValue(request, HashMap.class);
        String url = "/v2/balances/"+currencyCode;//+"/";//+ HashMapToQuiryPrams.hashMapToString(prams);

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
