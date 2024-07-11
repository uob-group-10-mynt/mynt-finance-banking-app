package com.mynt.banking.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
public class CurrencyCloudClientConfig {

    @Value("${currency.cloud.api.url}")
    private String apiUrl;

    @Value("${currency.cloud.api.userAgent}")
    private String USER_AGENT;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(apiUrl)
                .filter(LoggingInterceptor.logRequest())
                .filter(LoggingInterceptor.logResponse())
                .filter(ErrorHandlingInterceptor.handleErrors())
                .build();
    }

    static class LoggingInterceptor {
        static ExchangeFilterFunction logRequest() {
            return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
                System.out.println("Request: " + clientRequest.method() + " " + clientRequest.url());
                return Mono.just(clientRequest);
            });
        }

        static ExchangeFilterFunction logResponse() {
            return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
                System.out.println("Response status: " + clientResponse.statusCode());
                return Mono.just(clientResponse);
            });
        }
    }

    static class ErrorHandlingInterceptor {
        static ExchangeFilterFunction handleErrors() {
            return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
                if (clientResponse.statusCode().isError()) {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(body -> Mono.error(new WebClientResponseException(
                                    clientResponse.statusCode().value(),
                                    clientResponse.statusCode().toString(),
                                    clientResponse.headers().asHttpHeaders(),
                                    body.getBytes(),
                                    null)));
                }
                return Mono.just(clientResponse);
            });
        }
    }
}
