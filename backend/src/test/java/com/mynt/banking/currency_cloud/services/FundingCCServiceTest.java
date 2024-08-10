package com.mynt.banking.currency_cloud.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.Main;
import com.mynt.banking.currency_cloud.collect.demo.CurrencyCloudDemoService;
import com.mynt.banking.currency_cloud.collect.demo.requests.CurrencyCloudEmulateInboundFundsRequest;
import com.mynt.banking.currency_cloud.collect.funding.CurrencyCloudFundingService;
import com.mynt.banking.currency_cloud.collect.funding.requests.CurrencyCloudFindFundingAccountsRequest;
import com.mynt.banking.currency_cloud.manage.accounts.requests.CurrencyCloudFindAccountsRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Main.class)
public class FundingCCServiceTest {

    @Autowired
    private CurrencyCloudDemoService demoService;

    @Autowired
    private CurrencyCloudFundingService fundingService;

    @Test
    void testFindAccountDetails(){

        CurrencyCloudFindFundingAccountsRequest requestDto = CurrencyCloudFindFundingAccountsRequest.builder()
                .currency("GBP")
                .build();

        ResponseEntity<JsonNode> response = fundingService.findFundingAccounts(requestDto);

        assert response != null;
        assertEquals("81fdd4b8-b5c4-445f-844c-1490108529b8",response.getBody().get("funding_accounts").get(1).get("id").asText());

    }

    @Test
    void testFundDemoAccount(){
        CurrencyCloudFindFundingAccountsRequest requestDto = CurrencyCloudFindFundingAccountsRequest.builder()
                .currency("GBP")
                .build();

        ResponseEntity<JsonNode> response = fundingService.findFundingAccounts(requestDto);

        CurrencyCloudEmulateInboundFundsRequest demoFundingDto = CurrencyCloudEmulateInboundFundsRequest.builder()
                .id(response.getBody().get("funding_accounts").get(1).get("id").asText())
                .receiverAccountNumber(response.getBody().get("funding_accounts").get(1).get("account_number").asText())
                .amount(String.valueOf(10000))
                .currency(response.getBody().get("funding_accounts").get(1).get("currency").asText())
                .build();

        ResponseEntity<JsonNode> responseCreateFunds = demoService.emulateInboundFunds(demoFundingDto);

        assert responseCreateFunds != null;
        assertEquals("approved",responseCreateFunds.getBody().get("state").asText());
    }



}
