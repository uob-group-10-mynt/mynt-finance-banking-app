package com.mynt.banking.auth.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class SignUpRequest {

    @NotNull(message = "Email is required")
    @Email(message = "Email should be valid")
    @Schema(description = "Email", example = "James@Jameslove.com")
    @Size(max = 200, message = "Email cannot be longer than 100 characters")
    private String email;

    @NotNull(message = "Firstname is required")
    @Schema(description = "FirstName", example = "James")
    @Size(max = 100, message = "Firstname cannot be longer than 50 characters")
    private String firstname;

    @NotNull(message = "Lastname is required")
    @Schema(description = "Sername", example = "Love")
    @Size(max = 100, message = "Surname cannot be longer than 50 characters")
    private String lastname;

    @NotNull(message = "Date of Birth is required")
    @Schema(description = "DoB", example = "1066-8-16")
    @Size(max = 100, message = "dob cannot be longer than 50 characters")
    private String dob;

    @NotNull(message = "Address is required")
    @Schema(description = "bristol", example = "Bristol")
    @Size(max = 200, message = "address cannot be longer than 50 characters")
    private String address;

    @NotNull(message = "Phone number is requred")
    @Schema(description = "Phone Number", example = "+44 7834325342 ")
    @Size(max = 100, message = "phoneNumber cannot be longer than 50 characters")
    private String phoneNumber;

    @NotNull(message = "Password is required")
    @Schema(description = "Password", example = "Bristol2023")
    @Size(max = 100, message = "password cannot be longer than 50 characters")
    private String password;

}