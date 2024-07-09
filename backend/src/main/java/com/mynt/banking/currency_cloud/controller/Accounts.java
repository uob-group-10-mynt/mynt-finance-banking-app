package com.mynt.banking.currency_cloud.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mynt.banking.currency_cloud.dto.AccountRequest;
import com.mynt.banking.currency_cloud.service.CurrencyCloudAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/currency-cloud/accounts")
@RequiredArgsConstructor
public class Accounts {

    private final CurrencyCloudAPI currencyCloudAPI;

    @PostMapping("/create")
    public Mono<JsonNode> createAccount(@RequestBody AccountRequest requestBody) {
        return currencyCloudAPI.createAccount(requestBody);
    }
}
