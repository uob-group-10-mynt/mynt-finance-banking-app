package com.mynt.mynt.dto;


import jakarta.validation.constraints.*;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSignupRequest {

  @Email
  @NotBlank(message = "Email is Mandatory!")
  private String email;

  @Size(min = 8, max = 24, message = "Password has to be within 8 ~ 24 length")
  @NotBlank(message = "Password is Mandatory!")
  private String password;
}
