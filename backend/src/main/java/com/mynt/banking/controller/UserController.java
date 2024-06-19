package com.mynt.banking.controller;

import com.mynt.banking.dto.request.UserRequestDTO;
import com.mynt.banking.dto.response.UserResponseDTO;
import com.mynt.banking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO requestDTO) {
        // Convert UserRequestDTO to User entity and save
        UserResponseDTO responseDTO = userService.createUser(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        // Retrieve User by id and convert to UserResponseDTO
        UserResponseDTO responseDTO = userService.getUserById(id);
        return ResponseEntity.ok(responseDTO);
    }

    // Other CRUD endpoints can similarly use UserRequestDTO and UserResponseDTO
}
