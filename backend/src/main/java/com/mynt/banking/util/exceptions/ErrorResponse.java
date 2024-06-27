package com.mynt.banking.util.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public class ErrorResponse {

  private HttpStatusCode code;

  private String message;

  public static ErrorResponse of(HttpStatusCode code, String message) {
    return new ErrorResponse(code, message);
  }

  public static ErrorResponse of(ErrorCode errorCode) {
    return new ErrorResponse(errorCode.getStatus(), errorCode.getDescription());
  }

  public static ErrorResponse of(Exception e) {
    return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
  }
}
