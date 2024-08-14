package com.mynt.banking.mpesa;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import com.mynt.banking.currency_cloud.collect.demo.DemoService;
import com.mynt.banking.currency_cloud.collect.demo.DemoFundingDto;
import com.mynt.banking.currency_cloud.collect.funding.FindAccountDetailsRequest;
import com.mynt.banking.currency_cloud.collect.funding.FundingService;
import com.mynt.banking.currency_cloud.pay.beneficiaries.BeneficiaryService;
import com.mynt.banking.currency_cloud.pay.beneficiaries.FindBeneficiaryRequest;
import com.mynt.banking.currency_cloud.pay.payments.PaymentService;
import com.mynt.banking.currency_cloud.pay.payments.CreatePaymentRequest;
import com.mynt.banking.currency_cloud.repo.CurrencyCloudEntity;
import com.mynt.banking.currency_cloud.repo.CurrencyCloudRepository;
import com.mynt.banking.mpesa.requests.*;
import com.mynt.banking.user.User;
import com.mynt.banking.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FlutterwaveService {

    private final FlutterwaveWebClientConfig webClient;

    private final FundingService fundingService;

    private final DemoService demoService;

    private final BeneficiaryService beneficiariesService;

    @Value("${flutterwave.api.secretKey}")
    private String secretKey;

    private final UserRepository userRepository;

    private final CurrencyCloudRepository currencyCloudRepository;

    private final PaymentService paymentService;

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
                .flatMap(Mono::just);
    }

    public Mono<ResponseEntity<JsonNode>> wallet2Wallet(Wallet2WalletDto dto) {

        ObjectMapper mapper = new ObjectMapper();

        ObjectNode jsonNode = mapper.createObjectNode();
        jsonNode.put("amount", dto.getAmount());
        jsonNode.put("account_bank","flutterwave");
        jsonNode.put("account_number","200527841");
        jsonNode.put("currency","KES");
        jsonNode.put("debit_currency","NGN");

        String tx_ref = dto.getEmail()+LocalDateTime.now().toString();
        jsonNode.put("tx_ref", tx_ref);

        return webClient.webClientFW()
                .post()
                .uri("/v3/transfers")
                .header("Authorization", secretKey)
                .bodyValue(jsonNode)
                .exchangeToMono(response -> response.toEntity(JsonNode.class))
                .flatMap(Mono::just);
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
                .flatMap(Mono::just);
    }

    public String genTx_ref(String email){

        return email
                .replace(".", "_")
                .replace("@","_")
                +LocalDateTime.now().toString()
                .replace(":","_")
                .replace(".","_");
    }

    public Mono<ResponseEntity<JsonNode>> depositTransactionCheck(String id) {

        String url = "/v3/transactions/"+id+"/verify";
        return webClient.webClientFW()
                .get()
                .uri(url)
                .header("Authorization", secretKey)
                .exchangeToMono(response -> response.toEntity(JsonNode.class))
                .flatMap(Mono::just);

    }

    public Mono<ResponseEntity<JsonNode>> transactionCheck(String id) {
        String url = "/v3/transfers/"+id;
        return webClient.webClientFW()
                .get()
                .uri(url)
                .header("Authorization", secretKey)
                .exchangeToMono(response -> response.toEntity(JsonNode.class))
                .flatMap(Mono::just);

    }

    // mpesa to CC including CC methods
    public ResponseEntity<JsonNode> mpesaToCloudCurrency(MPesaToCurrencyCloudDto dto, String email) {

        ObjectMapper mapper = new ObjectMapper();
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()) {return null;}

        User userExists = user.get();

        // mPesaToFlutterwave()
        ResponseEntity<JsonNode> response = mPesaToFlutterwaveCall(dto, email, userExists);
        if(!response.getStatusCode().is2xxSuccessful()) { return response; }

        // depositTransactionCheck()
        response = depositTransactionCheckCall(response.getBody());
        if(!response.getStatusCode().is2xxSuccessful()) { return response; }

        // cc get account details end point
        response = ccFundAccountDetails(userExists);
        if(!response.getStatusCode().is2xxSuccessful()) { return response; }

        //cc demo fund account
        response =  demoFundAccount(userExists, response.getBody(),dto);
        if(!response.getStatusCode().is2xxSuccessful()) { return response; }

        // create custom response
        ObjectNode finalResponse = mapper.createObjectNode();
        finalResponse.put("transaction status","successful");
        response = ResponseEntity.ok(finalResponse);

        return response;
    }

    private ResponseEntity<JsonNode> mPesaToFlutterwaveCall(MPesaToCurrencyCloudDto dto, String email, User userExists){

        MPesaToFlutterWearDto mPesaToFlutterWearDto = MPesaToFlutterWearDto.builder()
                .amount(dto.getAmount())
                .email(userExists.getEmail())
                .phone_number(userExists.getPhone_number().replace("+", "0").replace(" ",""))
                .fullname(userExists.getFirstname()+" "+userExists.getLastname())
                .build();
        ResponseEntity<JsonNode> response = this.mPesaToFlutterwave(mPesaToFlutterWearDto).block();

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode errorResponse = mapper.createObjectNode();

        assert response != null;
        if(!response.getStatusCode().is2xxSuccessful()) {
            errorResponse.put("Error", " with mPesaToFlutterwave()");
            return ResponseEntity.status(400).body(errorResponse);
        }

        return response;
    }

    private ResponseEntity<JsonNode> depositTransactionCheckCall(JsonNode response){

        ResponseEntity<JsonNode> response1 =  this.depositTransactionCheck(response
                        .get("data")
                        .get("id")
                        .asText())
                        .block();

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode errorResponse = mapper.createObjectNode();

        assert response1 != null;
        if(!response1.getStatusCode().is2xxSuccessful()) {
            errorResponse.put("Error", " with depoistTransactionCheck()");
            return ResponseEntity.status(400).body(errorResponse);
        } else if (!(Objects.equals(response1.getBody().get("status").asText(), "success"))) {
            errorResponse.put("Error", " depoistTransactionCheck() is not sucessfull");
            return ResponseEntity.status(400).body(errorResponse);
        }

        return response1;

    }

    private ResponseEntity<JsonNode> ccFundAccountDetails(User user){

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode errorResponse = mapper.createObjectNode();

        // get UUID
        Optional<CurrencyCloudEntity> currencyCloudData = currencyCloudRepository.findByUser(user);
        String uuid = "";
        if(currencyCloudData.isPresent()) {
            uuid = currencyCloudData.get().getUuid();
        }

        FindAccountDetailsRequest data = FindAccountDetailsRequest.builder()
                .currency("KES")
                .onBehalfOf(uuid)
                .build();

        ResponseEntity<JsonNode> response = fundingService.find(data);

        // check for more than one account if so fail the test
        if(!Objects.equals(response.getBody().get("pagination").get("total_entries").toString(), "1")) {
            errorResponse.put("Error", " with ccFundAccountDetails()");
            return ResponseEntity.status(400).body(errorResponse);
        }

        if(!response.getStatusCode().is2xxSuccessful()) {
            errorResponse.put("Error", " with ccFundAccountDetails()");
            return ResponseEntity.status(400).body(errorResponse);
        }

        return response;
    }

    private ResponseEntity<JsonNode> demoFundAccount(User user, JsonNode ccFundAccountDetails, MPesaToCurrencyCloudDto dto){

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode errorResponse = mapper.createObjectNode();

        // get UUID
        Optional<CurrencyCloudEntity> currencyCloudData = currencyCloudRepository.findByUser(user);
        String uuid = "";
        if(currencyCloudData.isPresent()) {
            uuid = currencyCloudData.get().getUuid();
        }

        TimeBasedGenerator timeBasedGenerator = Generators.timeBasedGenerator();
        UUID idUUID = timeBasedGenerator.generate();

        DemoFundingDto demoFundingDto = DemoFundingDto.builder()
                .id(idUUID.toString()) // ccFundAccountDetails.get("funding_accounts").get(0).get("account_id").asText()
                .receiverAccountNumber(ccFundAccountDetails.get("funding_accounts").get(0).get("account_number").asText())
                .currency("KES")
                .amount(Integer.valueOf(dto.getAmount()))
                .onBehalfOf(uuid)
                .build();
        ResponseEntity<JsonNode> response =  demoService.create(demoFundingDto);

        if(!response.getStatusCode().is2xxSuccessful()) {
            errorResponse.put("Error", " with ccFundAccountDetails()");
            return ResponseEntity.status(400).body(errorResponse);
        }

        return response;

    }

    public ResponseEntity<JsonNode> cloudCurrency2Mpesa(CloudCurrency2MpesaDto request, String userEmail){

        ObjectMapper mapper = new ObjectMapper();
        Optional<User> user = userRepository.findByEmail(userEmail);
        if(user.isEmpty()) {return null;}
        User userExists = user.get();

        // send funds - from CC to Flutterwave (name == Flutterwave_KES_MPesa_Account)
        // /v2/beneficiaries/find
        ResponseEntity<JsonNode> response = this.findBeneficiary();
        if(!response.getStatusCode().is2xxSuccessful()) { return response; }
        String beneficiaryUUID = response.getBody().get("beneficiaries").get(0).get("id").asText() ;

        // /v2/payments/create
        response = this.payBeneficiary(beneficiaryUUID, userExists, request);
        if(!response.getStatusCode().is2xxSuccessful()) { return response; }

        // send funds - from flutterwave to Mpesa
        response = callSendMpesa(userExists, request);
        if(!response.getStatusCode().is2xxSuccessful()) { return response; }

        // check transaction status
        String id = response.getBody().get("data").get("id").asText();
        response = transactionCheck(id).block();
        if(!response.getStatusCode().is2xxSuccessful()) { return response; }
        if(!Objects.equals(response.getBody().get("status").asText(), "success")){
            ObjectNode errorResponse = mapper.createObjectNode();
            errorResponse.put("Error", " with sendMpesa()");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        // successful method return message
        ObjectNode finalResponse = mapper.createObjectNode();
        finalResponse.put("status", "successful transfer of funds to Beneficiaries MPesa Account");

        return ResponseEntity.ok(finalResponse);
    }

    private ResponseEntity<JsonNode> findBeneficiary(){
        FindBeneficiaryRequest dto = FindBeneficiaryRequest.builder()
                .name("Flutterwave_KES_MPesa_Account")
                .build();
        ResponseEntity<JsonNode> response = beneficiariesService.find(dto);

        if(!response.getStatusCode().is2xxSuccessful()) {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode errorResponse = mapper.createObjectNode();
            errorResponse.put("Error", " with ccFundAccountDetails()");
            return ResponseEntity.status(400).body(errorResponse);
        }

        return response;
    }

    private ResponseEntity<JsonNode> payBeneficiary(String beneficiaryUUID, User userExists, CloudCurrency2MpesaDto request){

        String ref = userExists.getEmail()+LocalDateTime.now();

        CreatePaymentRequest dto = CreatePaymentRequest.builder()
                .currency("KES")
                .beneficiaryId(beneficiaryUUID)
                .amount(request.getAmount().toString())
                .reason("Pay with M-Pesa")
                .reference(ref)
                .uniqueRequestId(ref)
                .build();

        ResponseEntity<JsonNode> response = paymentService.create(dto);

        if(!response.getStatusCode().is2xxSuccessful()) {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode errorResponse = mapper.createObjectNode();

            errorResponse.put("Error", " with ccFundAccountDetails()");

            return ResponseEntity.status(400).body(errorResponse);
        }

        return response;
    }

    private ResponseEntity<JsonNode> callSendMpesa(User userExists, CloudCurrency2MpesaDto request){
        SendMpesaDto dto = SendMpesaDto.builder()
                .amount(request.getAmount())
                .email(userExists.getEmail())
                .mobileNumber(request.getMobileNumber())
                .sender(userExists.getFirstname()+" "+userExists.getLastname())
                .beneficiaryName(request.getBeneficiaryName())
                .build();
        ResponseEntity<JsonNode> response = sendMPesa(dto).block();

        assert response != null;
        if(!response.getStatusCode().is2xxSuccessful()) {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode errorResponse = mapper.createObjectNode();
            errorResponse.put("Error", " with ccFundAccountDetails()");
            return ResponseEntity.status(400).body(errorResponse);
        }

        return response;
    }
}
