package com.mynt.banking.util.exceptions;

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
  public ResponseEntity<?> handleException(MyntExceptions e) {
    return ResponseEntity
        .status(e.getErrorCode().getStatus())
        .body(ResultResponseDTO.error(ErrorResponse.of(e)));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleException(Exception e) {
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ResultResponseDTO.error(ErrorResponse.of(e)));
  }
}
