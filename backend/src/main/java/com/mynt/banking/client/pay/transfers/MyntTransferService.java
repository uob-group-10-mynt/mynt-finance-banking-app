package com.mynt.banking.client.pay.transfers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonObject;
import com.mynt.banking.client.manage.balanaces.FindBalanceResponse;
import com.mynt.banking.currency_cloud.collect.funding.FindAccountDetailsRequest;
import com.mynt.banking.currency_cloud.collect.funding.FundingService;
import com.mynt.banking.currency_cloud.manage.balances.BalanceService;
import com.mynt.banking.currency_cloud.manage.contacts.ContactsService;
import com.mynt.banking.currency_cloud.manage.contacts.requestsDtos.FindContact;
import com.mynt.banking.currency_cloud.manage.transactions.TransactionService;
import com.mynt.banking.currency_cloud.pay.transfers.TransferService;
import com.mynt.banking.currency_cloud.pay.transfers.requests.CreateTransferRequest;
import com.mynt.banking.currency_cloud.repo.CurrencyCloudRepository;
import com.mynt.banking.user.User;
import com.mynt.banking.user.UserContextService;
import com.mynt.banking.user.UserRepository;
import io.swagger.v3.core.util.Json;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.processing.Find;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class MyntTransferService {

    private final UserContextService userContextService;
    private final TransferService transferService;
    private final UserRepository userRepository;
    private final ContactsService contactsService;

    public ResponseEntity<JsonNode> createTransfer(String emailTransferTo, String currency, double amount) {
        // current user UUID
        String sourceEmail = userContextService.getCurrentUsername();

        // get current user account ID
        FindContact findContact = FindContact.builder().emailAddress(sourceEmail).build();
        ResponseEntity<JsonNode> sourceAccountID = contactsService.findContact(findContact).block();


        if(sourceAccountID == null || !sourceAccountID.getStatusCode().is2xxSuccessful()){
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode jsonObject = mapper.createObjectNode();
            jsonObject.put("message", "Error with current User Account ID");
            return ResponseEntity.badRequest().body(jsonObject);
        }

        // recipient UUID
        String recipiantEmail =  userRepository.findByEmail(emailTransferTo).get().getEmail();

        // get recipient ID
        FindContact findRecipiantContact = FindContact.builder().emailAddress(recipiantEmail).build();
        ResponseEntity<JsonNode> recipiantAccountID = contactsService.findContact(findRecipiantContact).block();

        if(recipiantAccountID == null || !recipiantAccountID.getStatusCode().is2xxSuccessful()){
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode jsonObject = mapper.createObjectNode();
            jsonObject.put("message", "Error with recipiant User Account ID");
            return ResponseEntity.badRequest().body(jsonObject);
        }

        CreateTransferRequest createTransferRequest = CreateTransferRequest.builder()
                .amount(amount)
                .currency(currency)
                .sourceAccountId(sourceAccountID.getBody().get("contacts").get(0).get("account_id").asText())
                .destinationAccountId(recipiantAccountID.getBody().get("contacts").get(0).get("account_id").asText())
                .build();

        //call transfer
        ResponseEntity<JsonNode> response = this.transferService.createTransfer(createTransferRequest).block();

        if(response != null && response.getStatusCode().is2xxSuccessful()){
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode jsonObject = mapper.createObjectNode();
            jsonObject.put("message", "Transaction successfully created");
            return ResponseEntity.ok(jsonObject);
        }

        ObjectNode errorResponse = (ObjectNode)response.getBody();
        errorResponse.put("message", "Error with transaction \n transaction has failed");

        return ResponseEntity.badRequest().body(errorResponse);
    }
}

