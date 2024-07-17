package com.mynt.banking.currency_cloud.manage.contacts;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.manage.contacts.requestsDtos.CreateContact;
import com.mynt.banking.currency_cloud.manage.contacts.requestsDtos.FindContact;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/currency-cloud/contact")
@RequiredArgsConstructor
public class ContactController {

    private final ContactsService contactsService;

    @PostMapping("/create")
    public Mono<ResponseEntity<JsonNode>> create(@RequestBody CreateContact requestBody) {
        return contactsService.createContact(requestBody);
    }

    @PostMapping("/find")
    public Mono<ResponseEntity<JsonNode>> find(@RequestBody FindContact requestBody) {
        return contactsService.findContact(requestBody);
    }


}
