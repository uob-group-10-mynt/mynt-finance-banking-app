package com.mynt.banking.currency_cloud.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.dto.Account;
import com.mynt.banking.currency_cloud.dto.AccountRequest;
import com.mynt.banking.currency_cloud.dto.AuthenticationResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

public interface CurrencyCloudAPI {

    ///////////////////////////////////////////////////////////////////
    ///// AUTHENTICATE API ////////////////////////////////////////////

    /** API User Login */
    @PostMapping(value = "/v2/authenticate/api", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Mono<AuthenticationResponse> authenticate(
            @RequestHeader("User-Agent") String userAgent,
            @RequestParam("login_id") String loginId,
            @RequestParam("api_key") String apiKey
    );

    /** End API session */
    @PostMapping("/v2/authenticate/close_session")
    Mono<Object> endSession(
            @RequestHeader("X-Auth-Token") String authToken,
            @RequestHeader("User-Agent") String userAgent
    );

    ///////////////////////////////////////////////////////////////////
    ///// ACCOUNTS API ////////////////////////////////////////////////
    /** Create Account */
//    @PostMapping(value = "/v2/accounts/create", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//    String createAccount(@RequestBody AccountRequest requestBody);

    @PostMapping(value = "/v2/accounts/create", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Mono<ResponseEntity<Account>> createAccount(
//            @RequestHeader("X-Auth-Token") String authToken,
//            @RequestHeader("User-Agent") String userAgent,
            AccountRequest requestBody
    );


}
