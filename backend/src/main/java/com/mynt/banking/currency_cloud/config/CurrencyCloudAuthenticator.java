package com.mynt.banking.currency_cloud.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
public class CurrencyCloudAuthenticator {

    private String authToken;

    private static final long TOKEN_EXPIRE_MINUTES = 29;

    @Value("${currency.cloud.api.url}")
    private String apiUrl;

    @Value("${currency.cloud.api.userAgent}")
    private String userAgent;

    @Value("${currency.cloud.api.login_id}")
    private String loginId;

    @Value("${currency.cloud.api.key}")
    private String apiKey;

    private long lastAuthTime;

    @PostConstruct
    private void init() {
        authenticate();
    }

    public void authenticate() {
        this.lastAuthTime = Instant.now().getEpochSecond();

        Map<String, String> requestBodyMap = new HashMap<>();
        requestBodyMap.put("login_id", loginId);
        requestBodyMap.put("api_key", apiKey);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode requestBody = objectMapper.valueToTree(requestBodyMap);

        JsonNode response = RestClient
                .create()
                .post()
                .uri(apiUrl + "/v2/authenticate/api")
                .header("User-Agent", userAgent)
                .body(requestBody)
                .retrieve()
                .body(JsonNode.class);
        assert response != null;
        this.authToken = response.get("auth_token").asText();
        System.out.println(this.authToken);
    }

    public String getAuthToken() {
        if (Instant.now().getEpochSecond() - this.lastAuthTime > TOKEN_EXPIRE_MINUTES*60) {
            authenticate();
        }
        return authToken;
    }
}
