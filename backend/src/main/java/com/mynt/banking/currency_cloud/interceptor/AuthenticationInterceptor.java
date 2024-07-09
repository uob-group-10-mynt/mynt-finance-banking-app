package com.mynt.banking.currency_cloud.interceptor;

import com.mynt.banking.currency_cloud.service.AuthenticationService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class AuthenticationInterceptor {

    private final AuthenticationService authenticationService;
    private String authToken;

    @PostConstruct
    public void init() {
        refreshAuthToken().block();
    }

    public ExchangeFilterFunction applyAuthentication() {
        return ExchangeFilterFunction.ofRequestProcessor(this::addAuthToken);
    }

    private Mono<ClientRequest> addAuthToken(ClientRequest request) {
        // Check if the token is null or expired, then refresh it
        if (authToken == null) {
            return refreshAuthToken().flatMap(token -> {
                this.authToken = token;
                return Mono.just(ClientRequest.from(request)
                        .header("X-Auth-Token", authToken)
                        .build());
            });
        }
        return Mono.just(ClientRequest.from(request)
                .header("X-Auth-Token", authToken)
                .build());
    }

    private Mono<String> refreshAuthToken() {
        return authenticationService.getAuthToken().doOnNext(token -> this.authToken = token);
    }
}
