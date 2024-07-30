package com.mynt.banking.mPesa.flutterwave;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mynt.banking.currency_cloud.collect.demo.requests.DemoFundingDto;
import com.mynt.banking.currency_cloud.collect.funding.requests.FindAccountDetails;
import com.mynt.banking.currency_cloud.manage.authenticate.AuthenticationService;
import com.mynt.banking.mPesa.flutterwave.requests.MPesaToFlutterWearDto;
import com.mynt.banking.util.HashMapToQuiryPrams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class FlutterwaveService {

    private final FlutterwaveWebClientConfig webClient;

    @Value("${flutterwave.api.secretKey}")
    private String secretKey;

    //TODO: POST and GET Requests


    public Mono<ResponseEntity<JsonNode>> mPesaToFlutterWear(MPesaToFlutterWearDto mPesaToFlutterWearDto) {

        ObjectMapper mapper = new ObjectMapper();

        ObjectNode jsonNode = mapper.valueToTree(mPesaToFlutterWearDto);

        jsonNode.put("tx_ref", jsonNode.get("email").toString()+LocalDateTime.now().toString());

        return webClient.webClientFW()
                .post()
                .uri("/v3/charges?type=mpesa")
                .header("Authorization", secretKey)
                .bodyValue(jsonNode)
                .exchangeToMono(response -> response.toEntity(JsonNode.class))
                .flatMap(Mono::just);
    }


    public Mono<ResponseEntity<JsonNode>> depoistTransactionCheck(String id) {

        String url = "/v3/transactions/"+id+"/verify";
        return webClient.webClientFW()
                .get()
                .uri(url)
                .header("X-Auth-Token", secretKey)
                .exchangeToMono(response -> response.toEntity(JsonNode.class))
                .flatMap(request -> { return Mono.just(request); });

    }



}
