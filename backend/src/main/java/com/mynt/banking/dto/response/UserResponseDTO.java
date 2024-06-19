package com.mynt.banking.dto.response;

import com.mynt.banking.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    // You can include additional fields as needed
    public static UserResponseDTO fromEntity(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .firstName(user.getForename())
                .lastName(user.getSurname())
                .email(user.getEmail())
                .build();
    }
}


