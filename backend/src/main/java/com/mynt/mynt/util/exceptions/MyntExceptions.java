package com.mynt.mynt.util.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class MyntExceptions extends RuntimeException {

  private ErrorCode errorCode;

  public MyntExceptions(ErrorCode errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  public MyntExceptions(ErrorCode errorCode) {
    this.errorCode = errorCode;
  }
}
