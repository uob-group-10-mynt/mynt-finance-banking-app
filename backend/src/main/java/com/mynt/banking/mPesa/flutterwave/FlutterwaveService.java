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
import com.mynt.banking.mPesa.flutterwave.requests.Wallet2WalletDto;
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
                .flatMap(request -> { return Mono.just(request); });
    }

    public Mono<ResponseEntity<JsonNode>> wallet2Wallet(Wallet2WalletDto dto) {

        ObjectMapper mapper = new ObjectMapper();

        ObjectNode jsonNode = mapper.createObjectNode();
        jsonNode.put("amount", dto.getAmount());
        jsonNode.put("account_bank","flutterwave");
        jsonNode.put("account_number","200527841");
        jsonNode.put("currency","KES");
        jsonNode.put("debit_currency","NGN");

        String tx_ref = dto.getEmail().toString()+LocalDateTime.now().toString();
        jsonNode.put("tx_ref", tx_ref);

        return webClient.webClientFW()
                .post()
                .uri("/v3/transfers")
                .header("Authorization", secretKey)
                .bodyValue(jsonNode)
                .exchangeToMono(response -> response.toEntity(JsonNode.class))
                .flatMap(request -> { return Mono.just(request); });
    }

    public Mono<ResponseEntity<JsonNode>> depoistTransactionCheck(String id) {

        String url = "/v3/transactions/"+id+"/verify";
        return webClient.webClientFW()
                .get()
                .uri(url)
                .header("Authorization", secretKey)
                .exchangeToMono(response -> response.toEntity(JsonNode.class))
                .flatMap(request -> { return Mono.just(request); });

    }

    public Mono<ResponseEntity<JsonNode>> transactionCheck(String id) {

        String url = "/v3/transfers/"+id;
        return webClient.webClientFW()
                .get()
                .uri(url)
                .header("Authorization", secretKey)
                .exchangeToMono(response -> response.toEntity(JsonNode.class))
                .flatMap(request -> { return Mono.just(request); });

    }



}
