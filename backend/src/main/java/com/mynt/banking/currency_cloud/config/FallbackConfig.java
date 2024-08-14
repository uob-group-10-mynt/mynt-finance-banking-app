package com.mynt.banking.currency_cloud.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mynt.banking.util.exceptions.currency_cloud.CurrencyCloudException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FallbackConfig {

    public Object genericFallback(Throwable throwable, Object... params) {
        log.warn("Fallback method invoked due to an exception: {}", throwable.getMessage(), throwable);

        HttpStatus status;
        String message;

        if (throwable instanceof CurrencyCloudException) {
            status = HttpStatus.SERVICE_UNAVAILABLE;
            message = "Service Unavailable: " + throwable.getMessage();
        } else if (throwable.getMessage().contains("RateLimiter")) {
            status = HttpStatus.TOO_MANY_REQUESTS;
            message = "Rate limit exceeded. Please wait and try again later.";
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            message = "An unexpected error occurred. Unable to process the request at this time.";
        }

        // Return a JSON response if the method expects a ResponseEntity<JsonNode> or similar
        if (params.length > 0 && params[0] instanceof ResponseEntity) {
            return ResponseEntity.status(status).body(createFallbackResponse(message));
        }

        // Otherwise, throw a generic exception with the appropriate status
        throw new CurrencyCloudException(message, status);
    }

    private JsonNode createFallbackResponse(String message) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseNode = mapper.createObjectNode();
        responseNode.put("message", message);
        return responseNode;
    }
}
