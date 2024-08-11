package com.mynt.banking.currency_cloud.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.Main;
import com.mynt.banking.currency_cloud.collect.demo.DemoService;
import com.mynt.banking.currency_cloud.collect.demo.DemoFundingDto;
import com.mynt.banking.currency_cloud.collect.funding.FindAccountDetailsRequest;
import com.mynt.banking.currency_cloud.collect.funding.FundingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Main.class)
public class FundingCCServiceTest {

    @Autowired
    private DemoService demoService;

    @Autowired
    private FundingService fundingService;

    @Test
    void testFindAccountDetails(){

        FindAccountDetailsRequest requestDto = FindAccountDetailsRequest.builder()
                .currency("GBP")
                .build();

        ResponseEntity<JsonNode> response = fundingService.find(requestDto);

        assert response != null;
        assertEquals("81fdd4b8-b5c4-445f-844c-1490108529b8",response.getBody().get("funding_accounts").get(1).get("id").asText());

    }

    @Test
    void testFundDemoAccount(){
        FindAccountDetailsRequest requestDto = FindAccountDetailsRequest.builder()
                .currency("GBP")
                .build();

        ResponseEntity<JsonNode> response = fundingService.find(requestDto);

        DemoFundingDto demoFundingDto = DemoFundingDto.builder()
                .id(response.getBody().get("funding_accounts").get(1).get("id").asText())
                .receiverAccountNumber(response.getBody().get("funding_accounts").get(1).get("account_number").asText())
                .amount(10000)
                .currency(response.getBody().get("funding_accounts").get(1).get("currency").asText())
                .build();

        ResponseEntity<JsonNode> responseCreateFunds = demoService.create(demoFundingDto);

        assert responseCreateFunds != null;
        assertEquals("approved",responseCreateFunds.getBody().get("state").asText());
    }



}
