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
public class RegisterRequest {

    @Size(max = 50, message = "Firstname cannot be longer than 50 characters")
    private String firstname;

    @Size(max = 50, message = "Surname cannot be longer than 50 characters")
    private String lastname;

    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email cannot be longer than 100 characters")
    private String email;

    @Size(max = 50, message = "password cannot be longer than 50 characters")
    private String password;

    @NotBlank(message = "Role is required")
    private Role role;

    @Size(max = 50, message = "dob cannot be longer than 50 characters")
    private String dob;

    @Size(max = 50, message = "address cannot be longer than 50 characters")
    private String address;

    @Size(max = 50, message = "phoneNumber cannot be longer than 50 characters")
    private String phoneNumber;
}