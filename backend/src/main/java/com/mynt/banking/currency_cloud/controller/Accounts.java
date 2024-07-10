package com.mynt.banking.currency_cloud.controller;

import com.mynt.banking.currency_cloud.model.Account;
import com.mynt.banking.currency_cloud.service.CurrencyCloudAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class Accounts {

    private final CurrencyCloudAPI currencyCloudAPI;

    @PostMapping("/create")
    public Mono<ResponseEntity<Account>> createAccount(
            @RequestHeader("X-Auth-Token") String authToken,
            @RequestHeader("User-Agent") String userAgent,
            @RequestParam("account_name") String accountName,
            @RequestParam("legal_entity_type") String legalEntityType,
            @RequestParam("street") String street,
            @RequestParam("city") String city,
            @RequestParam("postal_code") String postalCode,
            @RequestParam("country") String country,
            @RequestParam(value = "state_or_province", required = false) String stateOrProvince,
            @RequestParam(value = "brand", required = false) String brand,
            @RequestParam(value = "your_reference", required = false) String yourReference,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "spread_table", required = false) String spreadTable,
            @RequestParam(value = "identification_type", required = false) String identificationType,
            @RequestParam(value = "identification_value", required = false) String identificationValue,
            @RequestParam(value = "api_trading", required = false) Boolean apiTrading,
            @RequestParam(value = "online_trading", required = false) Boolean onlineTrading,
            @RequestParam(value = "phone_trading", required = false) Boolean phoneTrading,
            @RequestParam(value = "terms_and_conditions_accepted", required = false) Boolean termsAndConditionsAccepted) {

        return currencyCloudAPI.createAccount(
                        authToken,
                        userAgent,
                        accountName,
                        legalEntityType,
                        street,
                        city,
                        postalCode,
                        country,
                        stateOrProvince,
                        brand,
                        yourReference,
                        status,
                        spreadTable,
                        identificationType,
                        identificationValue,
                        apiTrading,
                        onlineTrading,
                        phoneTrading,
                        termsAndConditionsAccepted
                )
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)));
    }
}
