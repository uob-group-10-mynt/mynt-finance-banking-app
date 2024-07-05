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
public class ValidateKycRequest {

//    @NotBlank(message = "Firstname is required")
//    @Size(max = 300, message = "Forename cannot be longer than 50 characters")
//    private String WORKFLOW_RUN_ID;

    @NotBlank(message = "Firstname is required")
    @Size(max = 300, message = "Forename cannot be longer than 50 characters")
    private String Email;


}