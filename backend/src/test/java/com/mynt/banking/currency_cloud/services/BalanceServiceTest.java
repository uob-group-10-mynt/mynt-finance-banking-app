package com.mynt.banking.currency_cloud.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mynt.banking.Main;
import com.mynt.banking.currency_cloud.manage.balances.requests.CurrencyCloudFindBalancesRequest;
import com.mynt.banking.currency_cloud.manage.balances.requests.CurrencyCloudGetBalanceRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Main.class)
public class BalanceServiceTest {

    @Autowired
    private com.mynt.banking.currency_cloud.manage.balances.CurrencyCloudBalancesService balanceService;

    @Test
    public void testBalanceCC() {
        CurrencyCloudFindBalancesRequest data = CurrencyCloudFindBalancesRequest.builder()
                .amountTo("")
                .onBehalfOf("")
                .amountFrom("")
                .asAtDate("")
                .page("")
                .perPage("")
                .order("")
                .build();

        ResponseEntity<JsonNode> response = balanceService.findBalances(data);

        assertNotNull(response);
        assertEquals(response.getStatusCode().value(),200);

        assertEquals(response.getBody().get("balances").get(0).get("currency").asText(),"USD");
        assertEquals(response.getBody().get("pagination").get("order").asText(),"created_at");

    }

    @Test
    public void testFindBalancesForParticularCurrency() throws JsonProcessingException {
        CurrencyCloudGetBalanceRequest data = CurrencyCloudGetBalanceRequest.builder()
                .onBehalfOf("")
                .build();

        ResponseEntity<JsonNode> response = balanceService.getBalance("GBP",data);

        ObjectMapper mapper = new ObjectMapper();
        String output = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response.getBody());

        assertNotNull(response);
        assertEquals(response.getStatusCode().value(),200);

        assertEquals(response.getBody().get("currency").asText(),"GBP");

    }
}
