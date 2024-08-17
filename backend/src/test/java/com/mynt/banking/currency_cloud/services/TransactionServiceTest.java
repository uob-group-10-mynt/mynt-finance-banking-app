package com.mynt.banking.currency_cloud.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.manage.transactions.TransactionService;
import com.mynt.banking.Main;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(classes = Main.class)
public class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @Test
    void testFindTransactionID() {

        ResponseEntity<JsonNode> response =  transactionService.findTransactionID("2259e26e-520a-4421-abe3-41748df64fde", "");

        assert response != null;
        assertEquals("USD",response.getBody().get("currency").asText());
        assertEquals("100000.00",response.getBody().get("amount").asText());

    }

}