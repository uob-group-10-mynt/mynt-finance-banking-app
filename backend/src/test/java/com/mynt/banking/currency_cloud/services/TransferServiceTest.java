package com.mynt.banking.currency_cloud.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.pay.transfers.TransferService;
import com.mynt.banking.currency_cloud.pay.transfers.CreateTransferRequest;
import com.mynt.banking.Main;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Main.class)
public class TransferServiceTest {

    @Autowired
    private TransferService transferService;

    @Test
    public void testCreateTransfer() {
        CreateTransferRequest transferRequest = CreateTransferRequest.builder()
                .uniqueRequestId(UUID.randomUUID().toString())
                .sourceAccountId("0339d3cf-f7da-4471-b5bb-db8daa577235")
                .destinationAccountId("b88968d1-8e87-4057-bb1c-9af86f12e7d8")
                .currency("KES")
                .amount(1000)
                .build();

        ResponseEntity<JsonNode> result = this.transferService.createTransfer(transferRequest);

        assert result != null;
        assert result.getBody() != null;
        assertEquals(result.getStatusCode().value(), 200);

        // "source_account_id": "0339d3cf-f7da-4471-b5bb-db8daa577235"
        assertEquals(result.getBody().get("source_account_id").asText(),"0339d3cf-f7da-4471-b5bb-db8daa577235");
        // "destination_account_id": "b88968d1-8e87-4057-bb1c-9af86f12e7d8"
        assertEquals(result.getBody().get("destination_account_id").asText(),"b88968d1-8e87-4057-bb1c-9af86f12e7d8");
        // "currency": "KES",
        assertEquals(result.getBody().get("currency").asText(),"KES");
        // "amount": "1000.00",
        assertEquals(result.getBody().get("amount").asInt(),1000);
        // "status": "pending",
        assertEquals(result.getBody().get("status").asText(),"pending");
    }
}
