package com.mynt.banking.currency_cloud.config;

import com.mynt.banking.util.exceptions.currency_cloud.CurrencyCloudException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import com.mynt.banking.currency_cloud.manage.authenticate.AuthenticationService;

@Component
public class WebClientErrorHandler {

    private final AuthenticationService authenticationService;
    private final WebClient webClient;


    public WebClientErrorHandler(AuthenticationService authenticationService, WebClient.Builder webClientBuilder) {
        this.authenticationService = authenticationService;
        this.webClient = webClientBuilder.build();
    }

    public Mono<? extends Throwable> handleUnauthorized(String uri) {
        return authenticationService.refreshAuthToken()
                .publishOn(Schedulers.boundedElastic())
                .flatMap(newToken -> webClient.get()
                        .uri(uri)
                        .header("X-Auth-Token", newToken)
                        .retrieve()
                        .toBodilessEntity()
                        .then(Mono.error(new RuntimeException("Unauthorized after retry"))));
    }

    public Mono<? extends Throwable> handleClientError(ClientResponse clientResponse) {
        return clientResponse.bodyToMono(String.class)
                .flatMap(errorBody -> Mono.error(
                        new CurrencyCloudException("Client error: " + errorBody, clientResponse.statusCode())));
    }

    public Mono<? extends Throwable> handleServerError(ClientResponse clientResponse) {
        return clientResponse.bodyToMono(String.class)
                .flatMap(errorBody -> Mono.error(
                        new CurrencyCloudException("Server error: " + errorBody, clientResponse.statusCode())));
    }
}


