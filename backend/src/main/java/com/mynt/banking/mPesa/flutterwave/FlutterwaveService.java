package com.mynt.banking.mPesa.flutterwave;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mynt.banking.currency_cloud.collect.demo.requests.DemoFundingDto;
import com.mynt.banking.currency_cloud.collect.funding.requests.FindAccountDetails;
import com.mynt.banking.currency_cloud.manage.authenticate.AuthenticationService;
import com.mynt.banking.mPesa.flutterwave.requests.MPesaToCurrencyCloudDto;
import com.mynt.banking.mPesa.flutterwave.requests.MPesaToFlutterWearDto;
import com.mynt.banking.mPesa.flutterwave.requests.SendMpesaDto;
import com.mynt.banking.mPesa.flutterwave.requests.Wallet2WalletDto;
import com.mynt.banking.user.UserContextService;
import com.mynt.banking.user.UserRepository;
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
    private final UserRepository userRepository;

    @Value("${flutterwave.api.secretKey}")
    private String secretKey;

    private final UserContextService userContextService;

    public Mono<ResponseEntity<JsonNode>> mPesaToFlutterwave(MPesaToFlutterWearDto mPesaToFlutterWearDto) {

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

    public Mono<ResponseEntity<JsonNode>> sendMPesa(SendMpesaDto dto) {

        ObjectMapper mapper = new ObjectMapper();

        ObjectNode meta = mapper.createObjectNode();
        meta.put("sender", dto.getSender());
        meta.put("sender_country", dto.getSenderCountry());
        meta.put("mobile_number", dto.getMobileNumber());

        ObjectNode jsonNode = mapper.createObjectNode();
        jsonNode.put("amount", dto.getAmount());
        jsonNode.put("account_bank","MPS");
        jsonNode.put("account_number","2540700000000");
        jsonNode.put("currency","KES");
        jsonNode.put("beneficiary_name", dto.getBeneficiaryName());
        jsonNode.put("debit_currency",dto.getDebitCurrency());
        jsonNode.set("meta",meta);

        String tx_ref = this.genTx_ref(dto.getEmail());
        jsonNode.put("reference", tx_ref);

        return webClient.webClientFW()
                .post()
                .uri("/v3/transfers")
                .header("Authorization", secretKey)
                .bodyValue(jsonNode)
                .exchangeToMono(response -> response.toEntity(JsonNode.class))
                .flatMap(request -> { return Mono.just(request); });
    }

    public String genTx_ref(String email){
        return email
                .replace(".", "_")
                .replace("@","_")
                +LocalDateTime.now().toString()
                .replace(":","_")
                .replace(".","_");
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

    //TODO: Intergrate - mpesa to CC including CC methrods
    public ResponseEntity<JsonNode> mpesaToCloudCurrency(MPesaToCurrencyCloudDto dto) {

        userContextService.getCurrentUsername();

        userRepository.findByEmail(userContextService.getCurrentUsername());


        //TODO: mPesaToFlutterwave()
        MPesaToFlutterWearDto mPesaToFlutterWearDto = MPesaToFlutterWearDto.builder()
                .amount()
                .email()
                .phone_number()
                .fullname()
                .build();
        ResponseEntity<JsonNode> response = this.mPesaToFlutterwave(mPesaToFlutterWearDto).block();

        //TODO: depoistTransactionCheck()
//        this.depoistTransactionCheck()

        //TODO: if sucessfull else return a 400

        //TODO: cc get account details end point

        //TODO: cc demo fund account

        return;
    }

}
