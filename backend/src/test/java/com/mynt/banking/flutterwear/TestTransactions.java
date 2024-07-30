package com.mynt.banking.flutterwear;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.Main;
import com.mynt.banking.mPesa.flutterwave.FlutterwaveService;
import com.mynt.banking.mPesa.flutterwave.requests.MPesaToFlutterWearDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Main.class)
public class TestTransactions {

    @Autowired
    private FlutterwaveService flutterwaveService;

    @Test
    void testmPesaToFlutterWear() {

        MPesaToFlutterWearDto mPesaToFlutterWearDto = MPesaToFlutterWearDto.builder().build();

        ResponseEntity<JsonNode> responce = flutterwaveService.mPesaToFlutterWear(mPesaToFlutterWearDto).block();

        assert responce != null;
        assertEquals(200,responce.getStatusCode().value());
        assertEquals("KES", responce.getBody().get("data").get("currency").asText());
    }

    @Test
    void testDepoistTransactionCheck(){

    }




}
