package com.mynt.banking.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class CurrencyCloudAuthClientConfig {

    @Value("${currency.cloud.api.url}")
    private String apiUrl;

    @Bean
    public WebClient authWebClient() {
        return WebClient.builder()
                .baseUrl(apiUrl)
                .build();
    }
}
