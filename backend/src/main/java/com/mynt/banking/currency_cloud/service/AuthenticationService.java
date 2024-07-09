package com.mynt.banking.currency_cloud.service;

import com.mynt.banking.currency_cloud.dto.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service("currencyCloudAuthenticationService")
public class AuthenticationService {

    private final WebClient authWebClient;

    @Value("${currency.cloud.api.userAgent}")
    private String userAgent;

    @Value("${currency.cloud.api.login_id}")
    private String loginId;

    @Value("${currency.cloud.api.key}")
    private String apiKey;

    public Mono<AuthenticationResponse> authenticate() {
        return authWebClient.post()
                .uri("/v2/authenticate/api")
                .header("User-Agent", userAgent)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("login_id", loginId)
                        .with("api_key", apiKey))
                .retrieve()
                .bodyToMono(AuthenticationResponse.class);
    }

    public Mono<String> getAuthToken() {
        return authenticate().map(AuthenticationResponse::getAuthToken);
    }
}
