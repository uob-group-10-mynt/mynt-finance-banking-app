package com.mynt.banking.currency_cloud.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.dto.AccountRequest;
import com.mynt.banking.currency_cloud.dto.AuthenticationResponse;
import org.springframework.http.MediaType;
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
    @PostMapping(value = "/v2/accounts/create", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Mono<JsonNode> createAccount(@RequestBody AccountRequest accountRequest);














//    @PostMapping(value = "/v2/accounts/create", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//    Mono<JsonNode> createAccount(
//            @RequestHeader("X-Auth-Token") String authToken,
//            @RequestHeader("User-Agent") String userAgent,
//            @RequestParam("account_name") String accountName,
//            @RequestParam("legal_entity_type") String legalEntityType,
//            @RequestParam("street") String street,
//            @RequestParam("city") String city,
//            @RequestParam("postal_code") String postalCode,
//            @RequestParam("country") String country,
//            @RequestParam(value = "state_or_province", required = false) String stateOrProvince,
//            @RequestParam(value = "brand", required = false) String brand,
//            @RequestParam(value = "your_reference", required = false) String yourReference,
//            @RequestParam(value = "status", required = false) String status,
//            @RequestParam(value = "spread_table", required = false) String spreadTable,
//            @RequestParam(value = "identification_type", required = false) String identificationType,
//            @RequestParam(value = "identification_value", required = false) String identificationValue,
//            @RequestParam(value = "api_trading", required = false) Boolean apiTrading,
//            @RequestParam(value = "online_trading", required = false) Boolean onlineTrading,
//            @RequestParam(value = "phone_trading", required = false) Boolean phoneTrading,
//            @RequestParam(value = "terms_and_conditions_accepted", required = false) Boolean termsAndConditionsAccepted
//    );


}
