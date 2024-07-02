package com.mynt.banking.config;

import com.currencycloud.client.exception.AuthenticationException;
import com.mynt.banking.currency_cloud.CurrencyCloudClientService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.io.IOException;

@Component
@AllArgsConstructor
public class CloudCurrencyAuthenticationInterceptor implements ClientHttpRequestInterceptor {

    private final CurrencyCloudClientService currencyCloudClientService;

    @Override
    @NonNull
    public ClientHttpResponse intercept(@Nonnull HttpRequest request,@Nonnull byte[] body, ClientHttpRequestExecution execution) throws IOException {
        ClientHttpResponse response = execution.execute(request, body);
        if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            try {
                currencyCloudClientService.authenticate();
            } catch (AuthenticationException e) {
                throw new IOException("Authentication failed", e);
            }
        }
        return response;
    }
}
