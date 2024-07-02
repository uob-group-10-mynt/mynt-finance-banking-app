package com.mynt.banking.config;

import com.currencycloud.client.CurrencyCloudClient;
import com.currencycloud.client.backoff.BackOffResultStatus;
import com.currencycloud.client.exception.ApiException;
import com.currencycloud.client.exception.TooManyRequestsException;
import com.currencycloud.client.backoff.BackOff;
import com.currencycloud.client.backoff.BackOffResult;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "currencycloud.api")
public class CurrencyCloudConfig {

    @Value("${currencycloud.api.url}")
    private String apiUrl;

    @Value("${currencycloud.api.login_id}")
    private String loginId;

    @Value("${currencycloud.api.key}")
    private String apiKey;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Bean
    public CurrencyCloudClient currencyCloudClient() {

        CurrencyCloudClient client = new CurrencyCloudClient(
                CurrencyCloudClient.Environment.demo,
                loginId,
                apiKey,
                CurrencyCloudClient.HttpClientConfiguration.builder()
                        .httpConnTimeout(15000)
                        .httpReadTimeout(35000)
                        .build());

        return Reauthenticator.createProxy(client);
    }

    private void authenticate(CurrencyCloudClient client) {
        try {
            client.authenticate();
            startTokenRefreshTask(client);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void startTokenRefreshTask(CurrencyCloudClient client) {
        scheduler.scheduleAtFixedRate(() -> {
            try {
                authenticate(client);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 25, 25, TimeUnit.MINUTES);  // Refresh every 25 minutes
    }
}