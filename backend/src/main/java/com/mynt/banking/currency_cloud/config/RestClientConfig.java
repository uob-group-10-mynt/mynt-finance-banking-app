package com.mynt.banking.currency_cloud.config;

import com.mynt.banking.currency_cloud.manage.authenticate.AuthenticationService;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
@RequiredArgsConstructor
@DependsOn("currencyCloudAuthenticationService")
public class RestClientConfig {

    @Value("${currency.cloud.api.url}")
    private String apiUrl;

    private final AuthenticationService authenticationService;

    private final RateLimiterRegistry rateLimiterRegistry;

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader("X-Auth-Token", authenticationService.getAuthToken())
//                .requestInterceptor(new AuthTokenInterceptor(authenticationService))
//                .requestInterceptor(new LoggingInterceptor())
//                .requestInterceptor(new RateLimitingInterceptor(rateLimiterRegistry))
                .build();
    }
}
