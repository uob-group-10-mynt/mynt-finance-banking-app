package com.mynt.banking.currency_cloud.service;

import com.mynt.banking.currency_cloud.dto.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Getter
@RequiredArgsConstructor
@Service("currencyCloudAuthenticationService")
public class AuthenticationService {

    private final WebClient webClient;

    private String authToken;

    @Value("${currency.cloud.api.url}")
    private String apiUrl;

    @Value("${currency.cloud.api.userAgent}")
    private String userAgent;

    @Value("${currency.cloud.api.login_id}")
    private String loginId;

    @Value("${currency.cloud.api.key}")
    private String apiKey;

    @PostConstruct
    public void init() {
        refreshAuthToken().block();
    }

    public Mono<AuthenticationResponse> authenticate() {
        return webClient.post()
                .uri("/v2/authenticate/api")
                .header("User-Agent", userAgent)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("login_id", loginId)
                        .with("api_key", apiKey))
                .retrieve()
                .bodyToMono(AuthenticationResponse.class);
    }

    @NotNull
    private Mono<String> refreshAuthToken() {
        return authenticate()
                .map(AuthenticationResponse::getAuthToken)
                .doOnNext(token -> this.authToken = token);
    }
}
