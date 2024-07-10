package com.mynt.banking.config;

import com.mynt.banking.currency_cloud.dto.AuthenticationResponse;
import com.mynt.banking.currency_cloud.service.CurrencyCloudAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

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

//        return WebClient.builder()
//                .baseUrl(apiUrl)
//                .defaultHeader("User-Agent", USER_AGENT)
//                .clientConnector(new ReactorClientHttpConnector(HttpClient.create()
//                        .responseTimeout(Duration.ofSeconds(10))))
//                .filter(LoggingInterceptor.logRequest())
//                .filter(LoggingInterceptor.logResponse())
//                .filter(ErrorHandlingInterceptor.handleErrors())
//                .build();
    }

    @Bean
    public CurrencyCloudAPI currencyCloudClient(WebClient webClient) {
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(webClient))
                .build();

        return factory.createClient(CurrencyCloudAPI.class);
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
