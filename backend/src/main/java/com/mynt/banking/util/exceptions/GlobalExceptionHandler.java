package com.mynt.banking.util.exceptions;

import com.mynt.banking.util.exceptions.authentication.KycException;
import com.mynt.banking.util.exceptions.authentication.TokenException;
import com.mynt.banking.util.exceptions.registration.RegistrationException;
import com.mynt.banking.util.exceptions.registration.UserAlreadyExistsException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

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
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, String>> handleAuthenticationException(@NotNull AuthenticationException ex, HttpServletRequest request) {
        return buildResponseEntity(HttpStatus.UNAUTHORIZED, ex.getMessage(), request);
    }


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
        return buildResponseEntity(HttpStatus.UNAUTHORIZED, "JWT: token processing error", request);
    }


    /// Response
    @NotNull
    @Contract("_, _, _ -> new")
    private ResponseEntity<Map<String, String>> buildResponseEntity(@NotNull HttpStatus status, String message, @NotNull HttpServletRequest request) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", String.valueOf(System.currentTimeMillis()));
        errorDetails.put("status", String.valueOf(status.value()));
        errorDetails.put("error", status.getReasonPhrase());
        errorDetails.put("message", message);
        errorDetails.put("path", request.getRequestURI());

        return new ResponseEntity<>(errorDetails, status);
    }
}
