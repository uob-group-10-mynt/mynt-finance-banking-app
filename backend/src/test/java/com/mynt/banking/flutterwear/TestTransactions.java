package com.mynt.banking.flutterwear;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.Main;
import com.mynt.banking.auth.KycService;
import com.mynt.banking.auth.requests.SignUpRequest;
import com.mynt.banking.auth.requests.ValidateKycRequest;
import com.mynt.banking.auth.responses.SDKResponse;
import com.mynt.banking.mPesa.flutterwave.FlutterwaveService;
import com.mynt.banking.mPesa.flutterwave.requests.MPesaToCurrencyCloudDto;
import com.mynt.banking.mPesa.flutterwave.requests.MPesaToFlutterWearDto;
import com.mynt.banking.mPesa.flutterwave.requests.SendMpesaDto;
import com.mynt.banking.mPesa.flutterwave.requests.Wallet2WalletDto;
import com.mynt.banking.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Main.class)
public class TestTransactions {

    @Autowired
    private FlutterwaveService flutterwaveService;

    @Autowired
    private KycService kycService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testmPesaToFlutterWear() {

        MPesaToFlutterWearDto mPesaToFlutterWearDto = MPesaToFlutterWearDto.builder().build();

        ResponseEntity<JsonNode> responce = flutterwaveService.mPesaToFlutterwave(mPesaToFlutterWearDto).block();

        assert responce != null;
        assertEquals(200,responce.getStatusCode().value());
        assertEquals("KES", responce.getBody().get("data").get("currency").asText());
    }

    @Test
    void testDepoistTransactionCheck(){

        MPesaToFlutterWearDto mPesaToFlutterWearDto = MPesaToFlutterWearDto.builder().build();
        ResponseEntity<JsonNode> responce = flutterwaveService.mPesaToFlutterwave(mPesaToFlutterWearDto).block();

        String id = responce.getBody().get("data").get("id").asText();
        ResponseEntity<JsonNode> responce1 = flutterwaveService.depoistTransactionCheck(id).block();

        assert responce1 != null;
        assertEquals(200,responce1.getStatusCode().value());
        String id1 = responce.getBody().get("data").get("id").asText();
        assertEquals(id1,responce1.getBody().get("data").get("id").asText());
    }

    @Test
    void testWallet2Wallet(){

        Wallet2WalletDto dto = Wallet2WalletDto.builder().build();
        ResponseEntity<JsonNode> responce = flutterwaveService.wallet2Wallet(dto).block();

        assert responce != null;
        assertEquals(200,responce.getStatusCode().value());
        assertEquals("KES",responce.getBody().get("data").get("currency").asText());

    }

    @Test
    void testSendMpesa(){

        SendMpesaDto dto = SendMpesaDto.builder().build();
        ResponseEntity<JsonNode> responce = flutterwaveService.sendMPesa(dto).block();

        assert responce != null;
        assertEquals(200,responce.getStatusCode().value());
        assertEquals("KES",responce.getBody().get("data").get("currency").asText());
        assertEquals("success",responce.getBody().get("status").asText());

    }

    @Test
    void testTransactionCheck(){

        Wallet2WalletDto dto = Wallet2WalletDto.builder().build();
        ResponseEntity<JsonNode> responce = flutterwaveService.wallet2Wallet(dto).block();

        String id = responce.getBody().get("data").get("id").asText();
        ResponseEntity<JsonNode> response1 = flutterwaveService.transactionCheck(id).block();

        assert response1 != null;
        assertEquals(200,response1.getStatusCode().value());
        assertEquals("KES",response1.getBody().get("data").get("currency").asText());
    }

    @Test
    void testMpesaToCloudCurrency() throws JsonProcessingException {

        //TODO: double check in the morning
        String email = "test-a"+String.valueOf(userRepository.count()+1)+"@test.com";

        SignUpRequest dto = SignUpRequest.builder()
                .email(email)
                .build();
        ResponseEntity<SDKResponse>  getOnfidoSDK = kycService.getOnfidoSDK(dto);

        MPesaToCurrencyCloudDto dto1 = MPesaToCurrencyCloudDto.builder().build();
        ResponseEntity<JsonNode> response = flutterwaveService.mpesaToCloudCurrency(dto1,email);

        assert response != null;
        assertEquals(200,response.getStatusCode().value());
        //TODO: create tests

    }


}
