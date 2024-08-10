package com.mynt.banking.currency_cloud.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.manage.transactions.CurrencyCloudTransactionsService;
import com.mynt.banking.Main;
import com.mynt.banking.currency_cloud.manage.transactions.requests.CurrencyCloudFindTransactionsRequest;
import com.mynt.banking.currency_cloud.manage.transactions.requests.CurrencyCloudGetTransactionRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Main.class)
public class TransactionsCCTests {

    @Autowired
    private CurrencyCloudTransactionsService transactionService;

    @Test
    void testTransactionsFind() {


        CurrencyCloudFindTransactionsRequest findTransaction = CurrencyCloudFindTransactionsRequest.builder()
                .build();

        ResponseEntity<JsonNode> response =  transactionService.findTransactions(findTransaction);

        assert response != null;
        assertEquals("USD",response.getBody().get("transactions").get(0).get("currency").asText());
        assertEquals( "100000.00",response.getBody().get("transactions").get(0).get("amount").asText());
    }

    @Test
    void testFindTransactionID() {
        CurrencyCloudGetTransactionRequest request = CurrencyCloudGetTransactionRequest.builder()
                .build();
        ResponseEntity<JsonNode> response =  transactionService.getTransaction("2259e26e-520a-4421-abe3-41748df64fde",request);

        assert response != null;
        assertEquals("USD",response.getBody().get("currency").asText());
        assertEquals("100000.00",response.getBody().get("amount").asText());

    }

}
