package com.mynt.banking.client.manage.balanaces;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.collect.funding.FindAccountDetailsRequest;
import com.mynt.banking.currency_cloud.collect.funding.FundingService;
import com.mynt.banking.currency_cloud.manage.balances.BalanceService;
import com.mynt.banking.user.UserContextService;
import com.mynt.banking.util.exceptions.currency_cloud.CurrencyCloudException;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// TODO remove object null exception and throw custom exceptions:

@Component
@RequiredArgsConstructor
public class MyntBalanceService {

    private final UserContextService userContextService;
    private final BalanceService balanceService;
    private final FundingService fundingService;

    public MyntFindBalanceResponse get(String currencyCode) {
        // Fetch balance:
        ResponseEntity<JsonNode> balanceResponse = balanceService.get(currencyCode,
                userContextService.getCurrentUserUuid());

        // Extract account_id and amount from balance response
        String accountId;
        String amount;
        try {
            accountId = Objects.requireNonNull(balanceResponse.getBody()).get("account_id").asText();
            amount = Objects.requireNonNull(balanceResponse.getBody()).get("amount").asText();
        }
        catch (NullPointerException ignore) {
            throw new CurrencyCloudException("Currency Cloud Error: Issue parsing necessary fields", HttpStatus.NO_CONTENT);
        }

        // Fetch bank account details:
        FindAccountDetailsRequest accountDetailsRequest = FindAccountDetailsRequest.builder()
                .accountId(accountId)
                .onBehalfOf(userContextService.getCurrentUserUuid())
                .currency(currencyCode)
                .paymentType("priority")
                .build();
        ResponseEntity<JsonNode> accountDetailsResponse = fundingService.findAccountDetails(accountDetailsRequest);

        // Extract fields from account details:
        JsonNode accountDetailsBody = Objects.requireNonNull(accountDetailsResponse.getBody()).get("funding_accounts").get(0);
        String accountNumberType = accountDetailsBody.get("account_number_type").asText();
        String accountNumber = accountDetailsBody.get("account_number").asText();
        String routingCodeType = accountDetailsBody.get("routing_code_type").asText();
        String routingCode = accountDetailsBody.get("routing_code").asText();
        String bankName = accountDetailsBody.get("bank_name").asText();

        // Generate Balance Response:
        return MyntFindBalanceResponse.builder()
                .bank(bankName)
                .label(currencyCode + " Currency Account")
                .accountNumberType(accountNumberType)
                .accountNumber(accountNumber)
                .routingCodeType(routingCodeType)
                .routingCode(routingCode)
                .currency(currencyCode)
                .balance(amount)
                .build();
    }


    public List<MyntFindBalanceResponse> find() {
        // Fetch balance:
        ResponseEntity<JsonNode> balancesResponse = balanceService.find(userContextService.getCurrentUserUuid());

        // Check if the "balances" array exists and has at least one entry and extract account_id:
        JsonNode balancesArray = Objects.requireNonNull(balancesResponse.getBody()).path("balances");

        List<MyntFindBalanceResponse> balancesResponseList = new ArrayList<>();
        if (balancesArray.isEmpty()) {
            throw new CurrencyCloudException("No content", HttpStatus.NO_CONTENT);
        }

        // Extract account_id from the first balance
        String accountId = balancesArray.get(0).path("account_id").asText();

        // Iterate through the balances array and create FindBalanceResponse DTOs
        for (JsonNode balanceNode : balancesArray) {
            String currency = balanceNode.path("currency").asText();
            String amount = balanceNode.path("amount").asText();

            // Fetch bank account details:
            MyntFindBalanceResponse findBalanceResponse = MyntFindBalanceResponse.builder()
                    .currency(currency)
                    .balance(amount)
                    .build();

            FindAccountDetailsRequest accountDetailsRequest = FindAccountDetailsRequest.builder()
                    .accountId(accountId)
                    .onBehalfOf(userContextService.getCurrentUserUuid())
                    .currency(findBalanceResponse.getCurrency())
                    .paymentType("priority")
                    .build();
            ResponseEntity<JsonNode> accountDetailsResponse = fundingService.findAccountDetails(accountDetailsRequest);

            // Extract fields from account details:
            JsonNode accountDetailsBody = Objects.requireNonNull(accountDetailsResponse.getBody()).get("funding_accounts").get(0);
            String accountNumberType = accountDetailsBody.get("account_number_type").asText();
            String accountNumber = accountDetailsBody.get("account_number").asText();
            String routingCodeType = accountDetailsBody.get("routing_code_type").asText();
            String routingCode = accountDetailsBody.get("routing_code").asText();
            String bankName = accountDetailsBody.get("bank_name").asText();

            // Populate the DTO with the rest of the details
            findBalanceResponse.setAccountNumberType(accountNumberType);
            findBalanceResponse.setAccountNumber(accountNumber);
            findBalanceResponse.setRoutingCodeType(routingCodeType);
            findBalanceResponse.setRoutingCode(routingCode);
            findBalanceResponse.setBank(bankName);
            findBalanceResponse.setLabel(findBalanceResponse.getCurrency() + " Currency Account");

            balancesResponseList.add(findBalanceResponse);
        }

        return balancesResponseList;
    }
}

