package com.mynt.banking.user.requests;
import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UpdateUserDetailsRequest {

    @NotNull(message = "Firstname is required")
    @Schema(description = "Firstname", example = "Kelvin")
    @Size(max = 100, message = "Firstname cannot be longer than 100 characters")
    @Pattern(regexp = "^[A-z]*$")
    private String firstname;

    @NotNull(message = "Last name is required")
    @Schema(description = "Last name", example = "Lu")
    @Size(max = 100, message = "Last name cannot be longer than 100 characters")
    @Pattern(regexp = "^[A-z]*$")
    private String lastname;

    @NotNull(message = "Address is required")
    @Schema(description = "Address", example = "Bristol")
    @Size(max = 200, message = "Address cannot be longer than 200 characters")
    private String address;

    @NotNull(message = "Phone number is required")
    @Schema(description = "Phone Number", example = "+44 7834325342 ")
    @Size(max = 100, message = "phoneNumber cannot be longer than 50 characters")
    private String phoneNumber;
}
