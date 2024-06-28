package com.mynt.banking.currency_cloud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
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

    private HttpHeaders createHeadersWithAuth() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return headers;
    }

    public String getBalance(String currency) {
        if (authToken == null) {
            authenticate();
        }

        String url = apiUrl + "/v2/balances/" + currency;

        HttpHeaders headers = createHeadersWithAuth();
        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        return response.getBody();
    }

    public Map<String, Object> createBeneficiary(Map<String, Object> beneficiaryDetails) {
        if (authToken == null) {
            authenticate();
        }

        String url = apiUrl + "/v2/beneficiaries/create";

        HttpHeaders headers = createHeadersWithAuth();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(beneficiaryDetails, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
        return response.getBody();
    }

    public Map<String, Object> getQuote(Map<String, Object> quoteDetails) {
        if (authToken == null) {
            authenticate();
        }

        String url = apiUrl + "/v2/rates/detailed";

        HttpHeaders headers = createHeadersWithAuth();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(quoteDetails, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
        return response.getBody();
    }

    public Map<String, Object> createConversion(Map<String, Object> conversionDetails) {
        if (authToken == null) authenticate();

        String url = apiUrl + "/v2/conversions/create";

        HttpHeaders headers = createHeadersWithAuth();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(conversionDetails, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
        return response.getBody();
    }

    public Map<String, Object> createPayment(Map<String, Object> paymentDetails) {
        if (authToken == null) authenticate();

        String url = apiUrl + "/v2/payments/create";

        HttpHeaders headers = createHeadersWithAuth();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(paymentDetails, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
        return response.getBody();
    }

    public Map<String, Object> authorizePayment(String paymentId) {
        if (authToken == null) authenticate();
        return null;
    }
}


