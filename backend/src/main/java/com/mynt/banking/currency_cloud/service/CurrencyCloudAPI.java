package com.mynt.banking.currency_cloud.service;

import com.mynt.banking.currency_cloud.dto.AuthenticationResponse;
import com.mynt.banking.currency_cloud.dto.CreateAccountRequestDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
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
    @PostMapping(value = "/v2/accounts/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    Mono<String> createAccount(CreateAccountRequestDto requestDto); //@RequestBody


}
