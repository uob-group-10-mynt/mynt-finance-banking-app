package com.mynt.banking.currency_cloud.manage.contacts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mynt.banking.currency_cloud.manage.authenticate.AuthenticationService;
import com.mynt.banking.currency_cloud.manage.contacts.requestsDtos.*;
import com.mynt.banking.util.HashMapToQuiryPrams;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.processing.Find;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class ContactsService {

    private final AuthenticationService authenticationService;
    private final WebClient webClient;

    public Mono<ResponseEntity<JsonNode>> createContact(CreateContact requestBody) {
        return webClient
                .post()
                .uri("/v2/contacts/create")
                .header("X-Auth-Token", authenticationService.getAuthToken())
                .bodyValue(requestBody)
                .exchangeToMono(response -> response.toEntity(JsonNode.class))
                .flatMap(response -> {
                    if(response.getStatusCode().is2xxSuccessful()) {
                        JsonNode jsonNode = response.getBody();
                        ResponseEntity<JsonNode> newResponseEntity = new ResponseEntity<>(jsonNode, response.getStatusCode());
                        return Mono.just(newResponseEntity);
                    }
                    return Mono.just(response);
                });
    }

    public Mono<ResponseEntity<JsonNode>> findContact(FindContact requestBody) {
        return webClient
                .post()
                .uri("/v2/contacts/find")
                .header("X-Auth-Token", authenticationService.getAuthToken())
                .bodyValue(requestBody)
                .exchangeToMono(response -> response.toEntity(JsonNode.class))
                .flatMap(response -> {
                    if(response.getStatusCode().is2xxSuccessful()) {
                        JsonNode jsonNode = response.getBody();
                        ResponseEntity<JsonNode> newResponseEntity = new ResponseEntity<>(jsonNode, response.getStatusCode());
                        return Mono.just(newResponseEntity);
                    }
                    return Mono.just(response);
                });

    }

    public Mono<ResponseEntity<JsonNode>> updateContact(
            String id,
            UpdateContactRequest request
    ) {

        ObjectMapper objectMapper = new ObjectMapper();

        HashMap<String, Object> prams = objectMapper.convertValue(request, HashMap.class);
        String url = "/v2/contacts/" + id + "" + HashMapToQuiryPrams.hashMapToString(prams);
        return webClient
                .post()
                .uri(url)
                .header("X-Auth-Token", authenticationService.getAuthToken())
                .bodyValue(request)
                .exchangeToMono(response -> response.toEntity(JsonNode.class))
                .flatMap(Mono::just);
    }

}
