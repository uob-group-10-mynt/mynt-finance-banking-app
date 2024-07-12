package com.mynt.banking.currency_cloud.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.dto.beneficiaries.FindBeneficiaries;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class BeneficiariesService {

    private final AuthenticationService authenticationService;
    private final WebClient webClient;

    public Mono<ResponseEntity<JsonNode>> find(FindBeneficiaries requestBody) {
        return webClient
                .post()
                .uri("/v2/beneficiaries/find")
                .header("X-Auth-Token", authenticationService.getAuthToken())
                .bodyValue(requestBody)
                .exchangeToMono(response -> response.toEntity(JsonNode.class))
                .flatMap(response -> {
                    if(response.getStatusCode().is2xxSuccessful()) {
                        // Exsample code
//                        JsonNode jsonNode = response.getBody();
//                        ObjectNode objectNode = ((ObjectNode) jsonNode).put("Custom Messsage","Hello World");
//                        ResponseEntity<JsonNode> newResponseEntity = new ResponseEntity<>(objectNode,response.getStatusCode());
//                        return Mono.just(newResponseEntity);
                        return Mono.just(response);
                    }
                    return Mono.just(response);
                });
    }

//    public Mono<ResponseEntity<JsonNode>> validate( requestBody) {
//        return webClient
//                .post()
//                .uri("/v2/beneficiaries/validate")
//                .header("X-Auth-Token", authenticationService.getAuthToken())
//                .bodyValue(requestBody)
//                .exchangeToMono(response -> response.toEntity(JsonNode.class))
//                .flatMap(response -> {
//                    if(response.getStatusCode().is2xxSuccessful()) {
//                        // Exsample code
////                        JsonNode jsonNode = response.getBody();
////                        ObjectNode objectNode = ((ObjectNode) jsonNode).put("Custom Messsage","Hello World");
////                        ResponseEntity<JsonNode> newResponseEntity = new ResponseEntity<>(objectNode,response.getStatusCode());
////                        return Mono.just(newResponseEntity);
//                        return Mono.just(response);
//                    }
//                    return Mono.just(response);
//                });

//    }
//    public Mono<ResponseEntity<JsonNode>> create( requestBody) {
//        return webClient
//                .post()
//                .uri("/v2/beneficiaries/create")
//                .header("X-Auth-Token", authenticationService.getAuthToken())
//                .bodyValue(requestBody)
//                .exchangeToMono(response -> response.toEntity(JsonNode.class))
//                .flatMap(response -> {
//                    if(response.getStatusCode().is2xxSuccessful()) {
//                        // Exsample code
////                        JsonNode jsonNode = response.getBody();
////                        ObjectNode objectNode = ((ObjectNode) jsonNode).put("Custom Messsage","Hello World");
////                        ResponseEntity<JsonNode> newResponseEntity = new ResponseEntity<>(objectNode,response.getStatusCode());
////                        return Mono.just(newResponseEntity);
//                        return Mono.just(response);
//                    }
//                    return Mono.just(response);
//                });

//    }


}
