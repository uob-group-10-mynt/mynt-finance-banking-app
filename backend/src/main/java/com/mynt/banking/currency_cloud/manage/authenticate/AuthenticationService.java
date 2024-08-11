package com.mynt.banking.currency_cloud.manage.authenticate;

import com.mynt.banking.util.exceptions.currency_cloud.CurrencyCloudException;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@Service("currencyCloudAuthenticationService")
public class AuthenticationService {

    private String authToken;

    private LocalDateTime tokenExpirationTime;

    @Value("${currency.cloud.api.url}")
    private String apiUrl;

    @Value("${currency.cloud.api.userAgent}")
    private String userAgent;

    @Value("${currency.cloud.api.login_id}")
    private String loginId;

    @Value("${currency.cloud.api.key}")
    private String apiKey;

    @PostConstruct
    private void init() { authenticate(); }

    private void authenticate() {
        AuthenticationRequest authRequest = AuthenticationRequest.builder()
                .loginId(loginId)
                .apiKey(apiKey)
                .build();

        AuthenticationResponse authResponse = authRestClient()
                .post()
                .uri(apiUrl + "/v2/authenticate/api")
                .header("User-Agent", userAgent)  // Add User-Agent header here
                .body(authRequest)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new CurrencyCloudException(response.getStatusText(), response.getStatusCode());
                })
                .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                    throw new CurrencyCloudException(response.getStatusText(), response.getStatusCode());
                })
                .body(AuthenticationResponse.class);

        if (authResponse == null) { throw new RuntimeException("Authentication Response is empty"); }

        this.authToken = authResponse.getAuthToken();
        // Set token expiration time to 29 minutes from now
        this.tokenExpirationTime = LocalDateTime.now().plusMinutes(29);
    }

    public void refreshAuthToken() { authenticate(); }

    public boolean isTokenExpired() { return LocalDateTime.now().isAfter(tokenExpirationTime); }

    private RestClient authRestClient() {
        return RestClient.builder()
                .baseUrl(apiUrl)
                .build();
    }
}
