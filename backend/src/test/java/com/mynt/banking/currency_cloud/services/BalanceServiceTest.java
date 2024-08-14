package com.mynt.banking.currency_cloud.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mynt.banking.Main;
import com.mynt.banking.currency_cloud.manage.balances.FindBalanceRequest;
import com.mynt.banking.currency_cloud.manage.balances.FindBalancesRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Main.class)
public class BalanceServiceTest {

    @Autowired
    private com.mynt.banking.currency_cloud.manage.balances.BalanceService balanceService;

    @Test
    public void testBalanceCC() {
        FindBalanceRequest data = FindBalanceRequest.builder()
                .amountTo("")
                .onBehalfOf("")
                .amountFrom("")
                .amountTo("")
                .asAtDate("")
                .createdAt("")
                .page("")
                .perPage("")
                .order("")
                .build();

        ResponseEntity<JsonNode> response = balanceService.find(data);

        assertNotNull(response);
        assertEquals(response.getStatusCode().value(),200);

        assertEquals(response.getBody().get("balances").get(0).get("currency").asText(),"USD");
        assertEquals(response.getBody().get("pagination").get("order").asText(),"created_at");

    }

    @Test
    public void testFindBalancesForParticularCurrency() throws JsonProcessingException {
        FindBalancesRequest data = FindBalancesRequest.builder()
                .onBehalfOf("")
                .build();

        ResponseEntity<JsonNode> response = balanceService.findForParticularCurrency(data,"GBP");

        ObjectMapper mapper = new ObjectMapper();
        String output = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response.getBody());

        assertNotNull(response);
        assertEquals(response.getStatusCode().value(),200);

        assertEquals(response.getBody().get("currency").asText(),"GBP");

    }
}
