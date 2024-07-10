package com.mynt.banking.config;

import com.mynt.banking.currency_cloud.interceptor.LoggingInterceptor;
import com.mynt.banking.currency_cloud.interceptor.ErrorHandlingInterceptor;
import com.mynt.banking.currency_cloud.interceptor.AuthenticationInterceptor;
import com.mynt.banking.currency_cloud.service.CurrencyCloudAPI;
import com.mynt.banking.currency_cloud.service.AuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class CurrencyCloudClientConfig {

    @Value("${currency.cloud.api.url}")
    private String apiUrl;

    private static final String USER_AGENT = "Mynt-SDK/1.0 Java/5.8.0";

    private final AuthenticationProvider authenticationProvider;

    @Bean
    CurrencyCloudAPI currencyCloudClient() {
        WebClient client = WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader("User-Agent", USER_AGENT)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create()
                        .responseTimeout(Duration.ofSeconds(10))))
                .filter(LoggingInterceptor.logRequest())
                .filter(LoggingInterceptor.logResponse())
                .filter(ErrorHandlingInterceptor.handleErrors())
                .filter(new AuthenticationInterceptor(authenticationProvider::getAuthToken).addAuthHeader())
                .build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(client))
                .build();

        return factory.createClient(CurrencyCloudAPI.class);
    }
}
