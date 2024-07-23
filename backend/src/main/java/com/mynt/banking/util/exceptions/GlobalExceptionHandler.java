package com.mynt.banking.util.exceptions;

import com.mynt.banking.auth.responses.AuthenticationResponse;
import com.mynt.banking.util.dto.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MyntExceptions.class)
  public ResponseEntity<ResultResponseDTO> handleMyntExceptions(MyntExceptions e) {
    return ResponseEntity
            .status(e.getErrorCode().getStatus())
            .body(ResultResponseDTO.error(ErrorResponse.of(e)));
  }

  @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
  public ResponseEntity<AuthenticationResponse> handleAuthenticationException(AuthenticationCredentialsNotFoundException e) {
    return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(new AuthenticationResponse("User not found or invalid credentials"));
  }

  @ExceptionHandler(KycNotApprovedException.class)
  public ResponseEntity<AuthenticationResponse> handleKycNotApprovedException(KycNotApprovedException e) {
    return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(new AuthenticationResponse(e.getMessage()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<AuthenticationResponse> handleGenericException(Exception e) {
    // Log the exception for debugging purposes
    log.error("An unexpected error occurred", e);
    return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new AuthenticationResponse("An unexpected error occurred"));
  }
}
