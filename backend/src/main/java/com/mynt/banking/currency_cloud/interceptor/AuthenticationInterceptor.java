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
        authenticationService.getAuthToken().subscribe(token -> this.authToken = token);
        System.out.println("AUTH_TOKEN: " + this.authToken);
    }

    public ExchangeFilterFunction applyAuthentication() {
        return ExchangeFilterFunction.ofRequestProcessor(this::addAuthToken);
    }

    private Mono<ClientRequest> addAuthToken(ClientRequest request) {
        if (authToken == null) {
            return authenticationService.getAuthToken().map(token -> {
                this.authToken = token;
                return ClientRequest.from(request)
                        .header("X-Auth-Token", authToken)
                        .build();
            });
        }
        return Mono.just(ClientRequest.from(request)
                .header("X-Auth-Token", authToken)
                .build());
    }
}
