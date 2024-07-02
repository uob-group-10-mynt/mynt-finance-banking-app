package com.mynt.banking.auth;

import com.mynt.banking.user.Role;

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
public class SignUpRequest {

    @NotBlank(message = "Firstname is required")
    @Size(max = 50, message = "Forename cannot be longer than 50 characters")
    private String firstname;

    @NotBlank(message = "Lastname is required")
    @Size(max = 50, message = "Surname cannot be longer than 50 characters")
    private String lastname;

//    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email cannot be longer than 100 characters")
    private String email;

//    @NotBlank(message = "Role is required")
//    private Role role;
}