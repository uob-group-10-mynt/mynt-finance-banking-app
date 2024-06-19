package com.mynt.banking.service;

import com.mynt.banking.dto.request.UserRequestDTO;
import com.mynt.banking.dto.response.UserResponseDTO;
import com.mynt.banking.entity.User;
import com.mynt.banking.repository.UserRepository;
import com.mynt.banking.util.exceptions.ErrorCode;
import com.mynt.banking.util.exceptions.MyntExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO requestDTO) {
        // Convert UserRequestDTO to User entity
        User user = UserRequestDTO.toEntity(requestDTO);

        // Save User entity
        User savedUser = userRepository.save(user);

        // Convert saved User entity to UserResponseDTO
        return UserResponseDTO.fromEntity(savedUser);
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Long id) {
        // Retrieve User entity by id
        User user = userRepository.findById(id)
                .orElseThrow(() -> new MyntExceptions(ErrorCode.USER_NOT_FOUND_ERROR));

        // Convert User entity to UserResponseDTO
        return UserResponseDTO.fromEntity(user);
    }

//    private User checkExistingByUserId(Long id) {
//
//    }
//
//    private User checkExistingUserByUserRequest() {
//
//    }

    // Other methods for updating, deleting, and additional business logic
}
