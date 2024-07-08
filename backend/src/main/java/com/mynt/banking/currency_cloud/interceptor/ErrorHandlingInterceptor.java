package com.mynt.banking.currency_cloud.interceptor;

import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;

public class ErrorHandlingInterceptor {

    public static ExchangeFilterFunction handleErrors() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            if (clientResponse.statusCode().isError()) {
                return clientResponse.bodyToMono(String.class)
                        .flatMap(body -> Mono.error(new RuntimeException("Error response: " + body)));
            }
            return Mono.just(clientResponse);
        });
    }
}
