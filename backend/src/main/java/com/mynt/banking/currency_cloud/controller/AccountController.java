package com.mynt.banking.currency_cloud.controller;

import com.mynt.banking.currency_cloud.dto.CreateAccountRequest;
import com.mynt.banking.currency_cloud.dto.CreateAccountResponse;
import com.mynt.banking.currency_cloud.service.CurrencyCloudAPI;
import com.mynt.banking.currency_cloud.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/currencycloud/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final CurrencyCloudAPI currencyCloudAPI;
    private final AuthenticationService authenticationService;

    @PostMapping("/create")
    public ResponseEntity<CreateAccountResponse> createAccount(@RequestBody CreateAccountRequest request) {
        String authToken = authenticationService.getAuthToken();

        return currencyCloudAPI.createAccount(authToken, request);
        // TODO test cc conection by manually seding request through here, then convert to dtos
//        return currencyCloudAPI.createAccount(
//                authToken,
//                request.getAccountName(),
//                request.getLegalEntityType(),
//                request.getStreet(),
//                request.getCity(),
//                request.getPostalCode(),
//                request.getCountry(),
//                request.getStateOrProvince(),
//                request.getBrand(),
//                request.getYourReference(),
//                request.getStatus(),
//                request.getSpreadTable(),
//                request.getIdentificationType(),
//                request.getIdentificationValue(),
//                request.getApiTrading(),
//                request.getOnlineTrading(),
//                request.getPhoneTrading(),
//                request.getTermsAndConditionsAccepted()
//        );
    }
}
