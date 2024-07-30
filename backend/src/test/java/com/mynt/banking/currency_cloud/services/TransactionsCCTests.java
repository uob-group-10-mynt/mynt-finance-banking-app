package com.mynt.banking.currency_cloud.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.manage.transactions.TransactionService;
import com.mynt.banking.currency_cloud.manage.transactions.requests.FindTransaction;
import com.mynt.banking.Main;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Main.class)
public class TransactionsCCTests {

    @Autowired
    private TransactionService transactionService;

    @Test
    void testTransactionsFind() {


        FindTransaction findTransaction = FindTransaction.builder()
                .build();

        ResponseEntity<JsonNode> response =  transactionService.find(findTransaction).block();

        assert response != null;
        assertEquals("USD",response.getBody().get("transactions").get(0).get("currency").asText());
        assertEquals( "100000.00",response.getBody().get("transactions").get(0).get("amount").asText());
    }

    @Test
    void testFindTransactionID() {

        ResponseEntity<JsonNode> response =  transactionService.findTransactionID("2259e26e-520a-4421-abe3-41748df64fde", "").block();

        assert response != null;
        assertEquals("USD",response.getBody().get("currency").asText());
        assertEquals("100000.00",response.getBody().get("amount").asText());

    }

}
