package com.mynt.banking.currency_cloud.config;

import com.mynt.banking.currency_cloud.manage.authenticate.AuthenticationService;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
@DependsOn("currencyCloudAuthenticationService")
public class RestClientConfig {

    @Value("${currency.cloud.api.url}")
    private String apiUrl;

    private final AuthenticationService authenticationService;
//    private final RateLimitingAndRetryInterceptor rateLimitingAndRetryInterceptor;
    private final LoggingInterceptor loggingInterceptor;

    @Bean
    public RestClient restClient() {
        ClientHttpRequestFactorySettings requestFactorySettings = ClientHttpRequestFactorySettings.DEFAULTS
                .withConnectTimeout(Duration.ofSeconds(1L))
                .withReadTimeout(Duration.ofSeconds(5L));
        JdkClientHttpRequestFactory requestFactory = ClientHttpRequestFactories.get(
                JdkClientHttpRequestFactory.class, requestFactorySettings);

        return RestClient.builder()
                .baseUrl(apiUrl)
                .requestFactory(new BufferingClientHttpRequestFactory(requestFactory))
                .defaultHeader("X-Auth-Token", authenticationService.getAuthToken())
                .requestInterceptor(new AuthTokenInterceptor(authenticationService))
                .requestInterceptor(loggingInterceptor)
//                .requestInterceptor(rateLimitingAndRetryInterceptor)
                .build();
    }
}
