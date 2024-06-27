package com.mynt.banking.util.exceptions;

import lombok.Getter;

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
