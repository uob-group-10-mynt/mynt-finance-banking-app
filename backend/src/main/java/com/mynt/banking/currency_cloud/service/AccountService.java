package com.mynt.banking.currency_cloud.service;

import com.mynt.banking.currency_cloud.dto.CreateAccountRequest;
import com.mynt.banking.currency_cloud.dto.CreateAccountResponse;
import com.mynt.banking.currency_cloud.dto.FindAccountRequest;
import com.mynt.banking.currency_cloud.dto.FindAccountResponse;
import com.mynt.banking.currency_cloud.utils.Utils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AccountService {

    private final AuthenticationService authenticationService;
    private final WebClient webClient;

    public AccountService(AuthenticationService authenticationService, WebClient.Builder webClientBuilder) {
        this.authenticationService = authenticationService;
        this.webClient = webClientBuilder.baseUrl("https://devapi.currencycloud.com").build();
    }

    public CreateAccountResponse createAccount(CreateAccountRequest request) {
        String authToken = authenticationService.getAuthToken();

        // Build the form data dynamically using MultiValueMap
        MultiValueMap<String, Object> formData = Utils.buildFormData(request);

        // Make the POST request
        Mono<CreateAccountResponse> createAccountResponseMono = webClient.post()
                .uri("/v2/accounts/create")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                .header("X-Auth-Token", authToken)
                .body(BodyInserters.fromMultipartData(formData))
                .retrieve()
                .bodyToMono(CreateAccountResponse.class);

        return createAccountResponseMono.block();
    }



    private MultiValueMap<String, Object> buildFormData(CreateAccountRequest request) {
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();

        // Required fields
        formData.add("account_name", request.getAccountName());
        formData.add("legal_entity_type", request.getLegalEntityType());
        formData.add("street", request.getStreet());
        formData.add("city", request.getCity());
        formData.add("postal_code", request.getPostalCode());
        formData.add("country", request.getCountry());

        // Optional fields - check if not null or empty before adding
        if (request.getStateOrProvince() != null && !request.getStateOrProvince().isEmpty()) {
            formData.add("state_or_province", request.getStateOrProvince());
        }
        if (request.getBrand() != null && !request.getBrand().isEmpty()) {
            formData.add("brand", request.getBrand());
        }
        if (request.getYourReference() != null && !request.getYourReference().isEmpty()) {
            formData.add("your_reference", request.getYourReference());
        }
        if (request.getStatus() != null && !request.getStatus().isEmpty()) {
            formData.add("status", request.getStatus());
        }
        if (request.getSpreadTable() != null && !request.getSpreadTable().isEmpty()) {
            formData.add("spread_table", request.getSpreadTable());
        }
        if (request.getIdentificationType() != null && !request.getIdentificationType().isEmpty()) {
            formData.add("identification_type", request.getIdentificationType());
        }
        if (request.getIdentificationValue() != null && !request.getIdentificationValue().isEmpty()) {
            formData.add("identification_value", request.getIdentificationValue());
        }
        if (request.getApiTrading() != null) {
            formData.add("api_trading", request.getApiTrading().toString());
        }
        if (request.getOnlineTrading() != null) {
            formData.add("online_trading", request.getOnlineTrading().toString());
        }
        if (request.getPhoneTrading() != null) {
            formData.add("phone_trading", request.getPhoneTrading().toString());
        }
        if (request.getTermsAndConditionsAccepted() != null) {
            formData.add("terms_and_conditions_accepted", request.getTermsAndConditionsAccepted().toString());
        }

        return formData;
    }
}
