package com.mynt.banking.currency_cloud.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class FallbackConfig {

    public ResponseEntity<JsonNode> rateLimiterFallback(Throwable throwable) {
        // Handle rate limiting fallback logic here
        return ResponseEntity.status(429)
                .body(createFallbackResponse("Rate limit exceeded. Please wait and try again later."));
    }

    public ResponseEntity<JsonNode> retryFallback(Throwable throwable) {
        // Handle retry fallback logic here
        return ResponseEntity.status(500)
                .body(createFallbackResponse("Service temporarily unavailable. Please wait and try again later."));
    }

    private JsonNode createFallbackResponse(String message) {
        // Create a JSON response with a custom message
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseNode = mapper.createObjectNode();
        responseNode.put("message", message);
        return responseNode;
    }
}
