package com.mynt.banking.auth.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
public class SignUpRequest {

    @NotNull(message = "Email is required")
    @Email(message = "Email should be valid")
    @Schema(description = "Email", example = "James@Jameslove.com")
    @Size(max = 200, message = "Email cannot be longer than 100 characters")
    @Builder.Default
    private String email = "James@Jameslove.com";

    @NotNull(message = "Firstname is required")
    @Schema(description = "FirstName", example = "James")
    @Size(max = 100, message = "Firstname cannot be longer than 50 characters")
    @Builder.Default
    private String firstname = "James";

    @NotNull(message = "Lastname is required")
    @Schema(description = "Sername", example = "Love")
    @Size(max = 100, message = "Surname cannot be longer than 50 characters")
    @Builder.Default
    private String lastname = "Love";

    @NotNull(message = "Date of Birth is required")
    @Schema(description = "DoB", example = "2020-8-16")
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    @Builder.Default
    private LocalDate dob = LocalDate.of(1066,8,16);

    @NotNull(message = "Address is required")
    @Schema(description = "bristol", example = "Bristol")
    @Size(max = 200, message = "address cannot be longer than 50 characters")
    @Builder.Default
    private String address = "Bristol";

    @NotNull(message = "Phone number is requred")
    @Schema(description = "Phone Number", example = "447834325342")
    @Size(max = 100, message = "phoneNumber cannot be longer than 50 characters")
    @Builder.Default
    private String phoneNumber = "447834325342";

    @NotNull(message = "Password is required")
    @Schema(description = "Password", example = "abc")
    @Size(max = 100, message = "password cannot be longer than 50 characters")
    @Builder.Default
    private String password = "abc";

}