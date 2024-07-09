package com.mynt.banking.currency_cloud.controller;

import com.mynt.banking.currency_cloud.dto.CreateAccountRequestDto;
import com.mynt.banking.currency_cloud.service.CurrencyCloudAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class Accounts {

    private final CurrencyCloudAPI currencyCloudAPI;

    @PostMapping("/create")
    public Mono<String> createAccount(@RequestBody CreateAccountRequestDto requestDto) {

        System.out.println("\n\n\naccountName");

        Mono<String> response = currencyCloudAPI.createAccount(requestDto)
                .doOnNext(response -> System.out.println("\n\n\n\ntest1: " + response))
                .doOnError(error -> System.err.println("Error creating account: " + error.getMessage()));

        return response;
    }

}
