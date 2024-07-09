package com.mynt.banking.currency_cloud.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mynt.banking.currency_cloud.dto.AccountRequest;
import com.mynt.banking.currency_cloud.service.AccountService;
import com.mynt.banking.currency_cloud.service.CurrencyCloudAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/currency-cloud/accounts")
@RequiredArgsConstructor
@Configuration
public class AccountController {

    private final AccountService accountService;



    @PostMapping("/create")
    public String createAccount(@RequestBody AccountRequest requestBody) {



        return this.accountService.createAccount(requestBody);

    }
}
