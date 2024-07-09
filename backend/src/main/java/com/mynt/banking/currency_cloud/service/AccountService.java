package com.mynt.banking.currency_cloud.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.dto.AccountRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final CurrencyCloudAPI currencyCloudAPI;

    public Mono<JsonNode> createAccount(AccountRequest accountRequest) {
        return currencyCloudAPI.createAccount(accountRequest);
    }

}
