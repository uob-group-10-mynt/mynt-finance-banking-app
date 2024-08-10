package com.mynt.banking.client.pay.transfers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mynt.banking.currency_cloud.manage.contacts.CurrencyCloudContactsService;
import com.mynt.banking.currency_cloud.manage.contacts.requests.*;
import com.mynt.banking.currency_cloud.pay.transfers.CurrencyCloudTransfersService;
import com.mynt.banking.currency_cloud.pay.transfers.requests.*;
import com.mynt.banking.user.UserContextService;
import com.mynt.banking.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyntTransferService {

    private final UserContextService userContextService;
    private final CurrencyCloudTransfersService transferService;
    private final UserRepository userRepository;
    private final CurrencyCloudContactsService contactsService;

    public ResponseEntity<JsonNode> createTransfer(String emailTransferTo, String currency, double amount) {
        // current user UUID
        String sourceEmail = userContextService.getCurrentUsername();

        // get current user account ID
        CurrencyCloudFindContactsRequest findContact = CurrencyCloudFindContactsRequest.builder().emailAddress(sourceEmail).build();
        ResponseEntity<JsonNode> sourceAccountID = contactsService.findContacts(findContact);


        if(sourceAccountID == null || !sourceAccountID.getStatusCode().is2xxSuccessful()){
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode jsonObject = mapper.createObjectNode();
            jsonObject.put("message", "Error with current User Account ID");
            return ResponseEntity.badRequest().body(jsonObject);
        }

        // recipient UUID
        String recipiantEmail =  userRepository.findByEmail(emailTransferTo).get().getEmail();

        // get recipient ID
        CurrencyCloudFindContactsRequest findRecipiantContact = CurrencyCloudFindContactsRequest.builder().emailAddress(recipiantEmail).build();
        ResponseEntity<JsonNode> recipiantAccountID = contactsService.findContacts(findRecipiantContact);

        if(recipiantAccountID == null || !recipiantAccountID.getStatusCode().is2xxSuccessful()){
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode jsonObject = mapper.createObjectNode();
            jsonObject.put("message", "Error with recipiant User Account ID");
            return ResponseEntity.badRequest().body(jsonObject);
        }

        CurrencyCloudCreateTransferRequest createTransferRequest = CurrencyCloudCreateTransferRequest.builder()
                .amount(String.valueOf(amount))
                .currency(currency)
                .sourceAccountId(sourceAccountID.getBody().get("contacts").get(0).get("account_id").asText())
                .destinationAccountId(recipiantAccountID.getBody().get("contacts").get(0).get("account_id").asText())
                .build();

        //call transfer
        ResponseEntity<JsonNode> response = this.transferService.createTransfer(createTransferRequest);

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

