package com.mynt.banking.currency_cloud.pay.transfers;


import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.manage.authenticate.AuthenticationService;
import com.mynt.banking.currency_cloud.pay.payments.requests.CreatePaymentRequest;
import com.mynt.banking.currency_cloud.pay.transfers.requests.CreateTransferRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final AuthenticationService authenticationService;
    private final WebClient webClient;

    public Mono<ResponseEntity<JsonNode>> createTransfer(CreateTransferRequest requestBody) {
        return webClient
                .post()
                .uri("/v2/transfers/create")
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
}