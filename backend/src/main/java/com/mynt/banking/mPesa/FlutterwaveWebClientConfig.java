package com.mynt.banking.mPesa;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class FlutterwaveWebClientConfig {

    @Value("${flutterwave.api.url}")
    private String apiUrl;

    @Bean
    public WebClient webClientFW() {
        return WebClient.builder()
                .baseUrl(apiUrl)
                .build();
    }

}








