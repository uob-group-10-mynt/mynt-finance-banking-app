<<<<<<<< HEAD:backend/src/main/java/com/mynt/banking/util/dto/ResultResponseDTO.java
package com.mynt.banking.util.dto;
========
package com.mynt.mynt.dto;
>>>>>>>> dev:backend/src/main/java/com/mynt/mynt/dto/ResultResponseDTO.java

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResultResponseDTO<T> {

  private static final String SUCCESS = "SUCCESS";
  private static final String ERROR = "ERROR";
  private String message;
  private final T result;

  public static <T> ResultResponseDTO<T> success() {
    return new ResultResponseDTO<>(SUCCESS, null);
  }

  public static <T> ResultResponseDTO<T> success(T object) {
    return new ResultResponseDTO<>(SUCCESS, object);
  }

  public static <T> ResultResponseDTO<T> error(T errorResponse) {
    return new ResultResponseDTO<>(ERROR, errorResponse);
  }
}
