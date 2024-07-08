package com.mynt.banking.currency_cloud.service;

import reactor.core.publisher.Mono;

public interface AuthenticationProvider {
    Mono<String> getAuthToken();
}
