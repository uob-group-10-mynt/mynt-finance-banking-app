package com.mynt.banking.auth.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email cannot be longer than 100 characters")
    @Schema(example = "alex@mail.com")
    private String email;

    @NotBlank(message = "Password is required")
    @Schema(example = "cake")
    @Size(max = 100, message = "Password cannot be longer than 100 characters")
    private String password;

}