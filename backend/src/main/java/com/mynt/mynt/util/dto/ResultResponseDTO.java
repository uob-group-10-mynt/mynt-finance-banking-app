package com.mynt.mynt.util.dto;

import com.mynt.mynt.util.exceptions.MyntExceptions;
import lombok.Getter;

import javax.xml.transform.Result;

@Getter
public class ResultResponseDTO<T> {

  private static final String SUCCESS = "SUCCESS";
  private static final String ERROR = "ERROR";
  private final T object;
  private String result;

  public ResultResponseDTO(String result, T object) {
    this.result = result;
    this.object = object;
  }

  public static <T> ResultResponseDTO<T> success() {
    return new ResultResponseDTO<>(SUCCESS, null);
  }

  public static <T> ResultResponseDTO<T> success(T object) {
    return new ResultResponseDTO<>(SUCCESS, object);
  }

  public static <T> ResultResponseDTO<T> error(T exception) {
    return new ResultResponseDTO<>(ERROR, exception);
  }
}
