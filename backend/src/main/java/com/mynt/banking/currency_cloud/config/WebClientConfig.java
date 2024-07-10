package com.mynt.banking.currency_cloud.config;

import com.mynt.banking.currency_cloud.interceptor.ErrorHandlingInterceptor;
import com.mynt.banking.currency_cloud.interceptor.LoggingInterceptor;
import io.netty.channel.ChannelOption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.ProxyProvider;

import java.time.Duration;

public class WebClientConfig {

    @Value("${currency.cloud.api.url}")
    private String apiUrl;

    @Value("${currency.cloud.api.userAgent}")
    private String USER_AGENT;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(apiUrl)
                .build();
    }
}

