package com.mynt.banking.auth;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthenticationFailureHandler implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
                         @NotNull AuthenticationException authException) throws IOException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", String.valueOf(System.currentTimeMillis()));
        errorDetails.put("status", String.valueOf(HttpStatus.UNAUTHORIZED.value()));
        errorDetails.put("error", HttpStatus.UNAUTHORIZED.getReasonPhrase());
        errorDetails.put("message", authException.getMessage());
        errorDetails.put("path", request.getRequestURI());

        response.getWriter().write(objectMapper.writeValueAsString(errorDetails));
    }
}
