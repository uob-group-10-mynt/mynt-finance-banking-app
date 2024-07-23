package com.mynt.banking.user.responses;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class GetUserDetailsResponse {
    private final String firstName;
    private final String lastName;
    private String dob;
    private String address;
    private String phoneNumber;
}
