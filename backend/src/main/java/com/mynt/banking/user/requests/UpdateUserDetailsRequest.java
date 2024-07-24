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

    @NotNull(message = "First name is required")
    @Schema(description = "First name", example = "James")
    @Size(max = 100, message = "First name cannot be longer than 50 characters")
    private String firstname;

    @NotNull(message = "Last name is required")
    @Schema(description = "Last name", example = "Love")
    @Size(max = 100, message = "Last name cannot be longer than 50 characters")
    private String lastname;

    @NotNull(message = "Date of birth is required")
    @Schema(description = "Date of birth", example = "1066-8-16")
    @Size(max = 50, message = "Date of birth cannot be longer than 50 characters")
    @DateTimeFormat(pattern = "dd MM yyyy")
    private LocalDate dob;

    @NotNull(message = "Address is required")
    @Schema(description = "Address", example = "Bristol")
    @Size(max = 200, message = "address cannot be longer than 50 characters")
    private String address;

    @NotNull(message = "Phone number is required")
    @Schema(description = "Phone Number", example = "+44 7834325342 ")
    @Size(max = 100, message = "phoneNumber cannot be longer than 50 characters")
    private String phoneNumber;
}
