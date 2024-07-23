package com.mynt.banking.user.requests;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UpdateUserDetailsRequest {

    @NotNull(message = "Firstname is required")
    @Schema(description = "FirstName", example = "James")
    @Size(max = 100, message = "Firstname cannot be longer than 50 characters")
    private String firstname;

    @NotNull(message = "Lastname is required")
    @Schema(description = "Surname", example = "Love")
    @Size(max = 100, message = "Surname cannot be longer than 50 characters")
    private String lastname;

    @NotNull(message = "Date of Birth is required")
    @Schema(description = "DoB", example = "1066-8-16")
    @DateTimeFormat(pattern = "dd MM yyyy")
    private LocalDate dob;

    @NotNull(message = "Address is required")
    @Schema(description = "bristol", example = "Bristol")
    @Size(max = 200, message = "address cannot be longer than 50 characters")
    private String address;

    @NotNull(message = "Phone number is requred")
    @Schema(description = "Phone Number", example = "+44 7834325342 ")
    @Size(max = 100, message = "phoneNumber cannot be longer than 50 characters")
    private String phoneNumber;
}
