package com.mynt.banking.currency_cloud.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mynt.banking.currency_cloud.dto.account.CreateAccountRequest;
import com.mynt.banking.currency_cloud.dto.account.FindAccountRequest;
import com.mynt.banking.currency_cloud.dto.account.FindAccountResponse;
import com.mynt.banking.currency_cloud.dto.beneficiaries.FindBeneficiaries;
import com.mynt.banking.currency_cloud.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AuthenticationService authenticationService;
    private final WebClient webClient;

    public Mono<ResponseEntity<JsonNode>> createAccount(CreateAccountRequest requestBody) {
        return webClient
                .post()
                .uri("/v2/accounts/create")
                .header("X-Auth-Token", authenticationService.getAuthToken())
                .bodyValue(requestBody)
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


    public FindAccountResponse findAccount(FindAccountRequest request) {
        String authToken = authenticationService.getAuthToken();

        // Build the form data dynamically using MultiValueMap
        MultiValueMap<String, Object> formData = Utils.buildFormData(request);

        // Make the POST request
        Mono<FindAccountResponse> findAccountResponseMono = webClient.post()
                .uri("/v2/accounts/find")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                .header("X-Auth-Token", authToken)
                .body(BodyInserters.fromMultipartData(formData))
                .retrieve()
                .bodyToMono(FindAccountResponse.class);

        return findAccountResponseMono.block();
    }

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
                        JsonNode jsonNode = response.getBody();
                        ObjectNode objectNode = ((ObjectNode) jsonNode).put("Custom Messsage","Hello World");
                        ResponseEntity<JsonNode> newResponseEntity = new ResponseEntity<>(objectNode,response.getStatusCode());
                        return Mono.just(newResponseEntity);
//                        return Mono.just(response);
                    }
                    return Mono.just(response);
                });
    }




}
