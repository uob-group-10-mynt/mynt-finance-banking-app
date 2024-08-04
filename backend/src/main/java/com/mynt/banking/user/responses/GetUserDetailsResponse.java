package com.mynt.banking.user.responses;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class GetUserDetailsResponse {
    private final String email;
    private final String firstname;
    private final String lastname;
    private final String dob;
    private final String address;
    private final String phoneNumber;
}
