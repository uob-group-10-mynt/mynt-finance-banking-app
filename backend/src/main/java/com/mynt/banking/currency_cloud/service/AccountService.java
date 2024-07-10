package com.mynt.banking.currency_cloud.service;

import com.mynt.banking.currency_cloud.dto.CreateAccountRequest;
import com.mynt.banking.currency_cloud.dto.CreateAccountResponse;
import com.mynt.banking.currency_cloud.dto.FindAccountRequest;
import com.mynt.banking.currency_cloud.dto.FindAccountResponse;
import com.mynt.banking.currency_cloud.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AuthenticationService authenticationService;
    private final WebClient webClient;


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

    public FindAccountResponse findAccount(FindAccountRequest request) {
        String authToken = authenticationService.getAuthToken();

        // Build the form data dynamically using MultiValueMap
        MultiValueMap<String, Object> formData = Utils.buildFormData(request);

        // Make the POST request
        Mono<FindAccountResponse> findAccountResponseMono = webClient.post()
                .uri("/v2/accounts/find")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                .header("X-Auth-Token", authToken)
                .body(BodyInserters.fromMultipartData(formData))
                .retrieve()
                .bodyToMono(FindAccountResponse.class);

        return findAccountResponseMono.block();
    }
}
