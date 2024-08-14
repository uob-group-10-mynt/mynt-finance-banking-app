package com.mynt.banking.util.exceptions;

import com.mynt.banking.util.exceptions.authentication.KycException;
import com.mynt.banking.util.exceptions.authentication.TokenException;
import com.mynt.banking.util.exceptions.currency_cloud.CurrencyCloudException;
import com.mynt.banking.util.exceptions.registration.RegistrationException;
import com.mynt.banking.util.exceptions.registration.UserAlreadyExistsException;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /// Registration Exceptions:
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyExistsException(@NotNull UserAlreadyExistsException ex, HttpServletRequest request) {
        return buildResponseEntity(HttpStatus.CONFLICT, ex.getMessage(), request);
    }
    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<Map<String, String>> handleRegistrationException(@NotNull RegistrationException ex, HttpServletRequest request) {
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request);
    }


    /// Authentication Exceptions:
    @ExceptionHandler(KycException.class)
    public ResponseEntity<Map<String, String>> handleKycException(@NotNull KycException ex, HttpServletRequest request) {
        HttpStatus status = ex instanceof KycException.KycStatusNotFoundException ? HttpStatus.NOT_FOUND : HttpStatus.FORBIDDEN;
        return buildResponseEntity(status, ex.getMessage(), request);
    }
    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleAuthenticationCredentialsNotFoundException(@NotNull AuthenticationCredentialsNotFoundException ex, HttpServletRequest request) {
        return buildResponseEntity(HttpStatus.UNAUTHORIZED, ex.getMessage(), request);
    }
    @ExceptionHandler({AuthenticationException.class, AccessDeniedException.class})
    public void throwSecurityException(Exception e) throws Exception { throw e; }


    /// Token Exceptions:
    @ExceptionHandler(TokenException.TokenGenerationException.class)
    public ResponseEntity<Map<String, String>> handleTokenGenerationException(@NotNull TokenException.TokenGenerationException ex, HttpServletRequest request) {
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "JWT: token generation error", request);
    }
    @ExceptionHandler(TokenException.TokenValidationException.class)
    public ResponseEntity<Map<String, String>> handleTokenValidationException(@NotNull TokenException.TokenValidationException ex, HttpServletRequest request) {
        return buildResponseEntity(HttpStatus.UNAUTHORIZED, "JWT: token validation failed", request);
    }
    @ExceptionHandler(TokenException.TokenRefreshException.class)
    public ResponseEntity<Map<String, String>> handleTokenRefreshException(@NotNull TokenException.TokenRefreshException ex, HttpServletRequest request) {
        return buildResponseEntity(HttpStatus.UNAUTHORIZED, "JWT: token refresh error", request);
    }


    /// Generic Exceptions:
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex, HttpServletRequest request) {
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", request);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(@NotNull RuntimeException ex, HttpServletRequest request) {
        log.info("REQUEST URI: {}", request.getRequestURI());
        log.info("EXCEPTION MESSAGE: {}", ex.getMessage());
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", request);
    }


    /// Rate Limiting and Service Unavailable Exceptions:
    @ExceptionHandler(RequestNotPermitted.class)
    public ResponseEntity<Map<String, String>> handleRateLimiterException(RequestNotPermitted ex, HttpServletRequest request) {
        log.info("Rate limiter triggered: {}", ex.getMessage(), ex);
        return buildResponseEntity(HttpStatus.TOO_MANY_REQUESTS, "Rate limit exceeded. Please wait and try again later.", request);
    }
    @ExceptionHandler(CurrencyCloudException.class)
    public ResponseEntity<Map<String, String>> handleCloudCurrencyException(CurrencyCloudException ex, HttpServletRequest request) {
        log.info("Service unavailable: {}", ex.getMessage(), ex);
        return buildResponseEntity(HttpStatus.SERVICE_UNAVAILABLE, "Service Unavailable: " + ex.getMessage(), request);
    }

//    // Currency Cloud Exceptions
//    @ExceptionHandler(CurrencyCloudException.class)
//    public ResponseEntity<Map<String, String>> handleCloudCurrencyException(CurrencyCloudException ex) {
//        return buildResponseEntity(HttpStatus.valueOf(ex.getStatus().value()), ex.getMessage(), null);
//    }

    /// Response
    @NotNull
    private ResponseEntity<Map<String, String>> buildResponseEntity(@NotNull HttpStatus status, String message, HttpServletRequest request) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", String.valueOf(System.currentTimeMillis()));
        errorDetails.put("status", String.valueOf(status.value()));
        errorDetails.put("error", status.getReasonPhrase());
        errorDetails.put("message", message);
        if (request != null) { errorDetails.put("path", request.getRequestURI()); }

        return new ResponseEntity<>(errorDetails, status);
    }
}
