package com.mynt.mynt.controller;

import com.mynt.mynt.dto.UserSignupRequest;
import com.mynt.mynt.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

  private final UserService userService;

  @PostMapping
  public ResponseEntity<?> signup(@Valid @RequestBody UserSignupRequest request) {
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(userService.signup(request));
  }
}
