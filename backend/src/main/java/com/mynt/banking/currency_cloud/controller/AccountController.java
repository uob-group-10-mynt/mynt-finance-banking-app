package com.mynt.banking.currency_cloud.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.dto.account.CreateAccountRequest;
import com.mynt.banking.currency_cloud.dto.account.FindAccountRequest;
import com.mynt.banking.currency_cloud.dto.account.FindAccountResponse;
import com.mynt.banking.currency_cloud.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/currency-cloud/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/create")
    public Mono<ResponseEntity<JsonNode>> createAccount(@RequestBody CreateAccountRequest request) {
        return accountService.createAccount(request);
    }

    @PostMapping("/find")
    public Mono<ResponseEntity<JsonNode>> findAccount(@RequestBody FindAccountRequest request) {
        return accountService.findAccount(request);
    }
}
