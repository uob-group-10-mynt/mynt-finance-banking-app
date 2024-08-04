package com.mynt.banking.user;

import com.mynt.banking.user.requests.*;
import com.mynt.banking.user.responses.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.security.Principal;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PatchMapping
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request, Principal connectedUser) {
        userService.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getUserDetails")
    public Mono<ResponseEntity<GetUserDetailsResponse>> getUserDetails() {
        GetUserDetailsResponse response;
        try {
            response = userService.getUserDetails();
        }
        catch (IOException e) {
            return Mono.just(ResponseEntity.badRequest().build());
        }
        return Mono.just(ResponseEntity.ok(response));
    }

    @PostMapping("/updateUserDetails")
    public Mono<ResponseEntity<UpdateUserDetailsRequest>> updateUserDetails(@RequestBody UpdateUserDetailsRequest request) {
        try {
            userService.updateUserDetails(request);
        }
        catch (Exception e) {
            return Mono.just(ResponseEntity.badRequest().build());
        }
        return Mono.just(ResponseEntity.ok().build());
    }
}
