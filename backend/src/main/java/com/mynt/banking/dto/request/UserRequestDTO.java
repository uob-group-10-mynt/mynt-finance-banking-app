package com.mynt.banking.dto.request;

import com.mynt.banking.dto.response.UserResponseDTO;
import com.mynt.banking.entity.User;
import lombok.Data;

@Data
public class UserRequestDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    // You can add validation annotations here if needed
    public static User toEntity(UserRequestDTO requestDTO) {
        return User.builder()
                .forename(requestDTO.getFirstName())
                .surname(requestDTO.getLastName())
                .email(requestDTO.getEmail())
                .password(requestDTO.getPassword())
                .build();
    }
}
