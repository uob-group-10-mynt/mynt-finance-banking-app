package com.mynt.banking.currency_cloud.service;

import com.mynt.banking.currency_cloud.model.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service("currencyCloudAuthenticationService")
public class AuthenticationService implements AuthenticationProvider {

    private final CurrencyCloudAPI currencyCloudAPI;

    @Value("${currency.cloud.api.userAgent}")
    private String userAgent;

    @Value("${currency.cloud.api.loginId}")
    private String loginId;

    @Value("${currency.cloud.api.apiKey}")
    private String apiKey;

    public Mono<AuthenticationResponse> authenticate() {
        return currencyCloudAPI.authenticate(userAgent, loginId, apiKey);
    }

    public Mono<Void> endSession(String authToken) {
        return currencyCloudAPI.endSession(authToken, userAgent).then();
    }

    @Override
    public Mono<String> getAuthToken() {
        return authenticate().map(AuthenticationResponse::getAuthToken);
    }
}
