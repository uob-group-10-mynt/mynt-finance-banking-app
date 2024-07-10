package com.mynt.banking.currency_cloud.interceptor;

import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

public class AuthenticationInterceptor {

    private final Supplier<Mono<String>> authTokenSupplier;

    public AuthenticationInterceptor(Supplier<Mono<String>> authTokenSupplier) {
        this.authTokenSupplier = authTokenSupplier;
    }

    public ExchangeFilterFunction addAuthHeader() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest ->
                authTokenSupplier.get().flatMap(authToken -> {
                    ClientRequest newRequest = ClientRequest.from(clientRequest)
                            .header("X-Auth-Token", authToken)
                            .build();
                    return Mono.just(newRequest);
                })
        );
    }
}
