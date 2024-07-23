package com.mynt.banking.util.exceptions;

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

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(@NotNull UserNotFoundException ex, HttpServletRequest request) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyExistsException(@NotNull UserAlreadyExistsException ex, HttpServletRequest request) {
        return buildResponseEntity(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler(KycNotApprovedException.class)
    public ResponseEntity<Map<String, String>> handleKycNotApprovedException(@NotNull KycNotApprovedException ex, HttpServletRequest request) {
        return buildResponseEntity(HttpStatus.FORBIDDEN, ex.getMessage(), request);
    }

    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<Map<String, String>> handleRegistrationException(@NotNull RegistrationException ex, HttpServletRequest request) {
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request);
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleAuthenticationCredentialsNotFoundException(@NotNull AuthenticationCredentialsNotFoundException ex, HttpServletRequest request) {
        return buildResponseEntity(HttpStatus.UNAUTHORIZED, ex.getMessage(), request);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, String>> handleAuthenticationException(@NotNull AuthenticationException ex, HttpServletRequest request) {
        return buildResponseEntity(HttpStatus.UNAUTHORIZED, ex.getMessage(), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex, HttpServletRequest request) {
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", request);
    }

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
