package com.mynt.banking.currency_cloud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class CurrencyCloudService {

    private final RestTemplate restTemplate;
    private String authToken;

    @Value("${currencycloud.api.url}")
    private String apiUrl;

    @Value("${currencycloud.api.login_id}")
    private String loginId;

    @Value("${currencycloud.api.key}")
    private String apiKey;

    public CurrencyCloudService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void authenticate() {
        String url = apiUrl + "/v2/authenticate/api";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("login_id", loginId);
        requestBody.put("api_key", apiKey);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

        Map<String, String> response = restTemplate.postForObject(url, request, Map.class);
        if (response != null && response.containsKey("auth_token")) {
            authToken = response.get("auth_token");
        }
    }

    public String getBalance() {
        if (authToken == null) {
            authenticate();
        }

        String url = apiUrl + "/v2/balances/find";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);

        HttpEntity<String> request = new HttpEntity<>(headers);

        return restTemplate.getForObject(url, String.class, request);
    }

    // Add more methods for other API interactions as needed
}
