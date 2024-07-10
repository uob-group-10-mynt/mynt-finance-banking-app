package com.mynt.banking.currency_cloud.controller;

import com.mynt.banking.currency_cloud.dto.CreateAccountRequest;
import com.mynt.banking.currency_cloud.dto.CreateAccountResponse;
import com.mynt.banking.currency_cloud.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/currency-cloud/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/create")
    public CreateAccountResponse createAccount(@RequestBody CreateAccountRequest request) {
            return accountService.createAccount(request);
    }
}
