package com.mynt.banking.util.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  USER_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "USER NOT FOUND!"),
  CURRENCY_CLOUD_API_CONNECTION_ERROR(HttpStatus.FAILED_DEPENDENCY, "CURRENCY CLOUD CONNECTION FAILURE")
  ;

  private final HttpStatus status;
  private final String description;
}
