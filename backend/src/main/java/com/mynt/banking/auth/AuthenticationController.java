package com.mynt.banking.auth;

import com.mynt.banking.auth.requests.AuthenticationRequest;
import com.mynt.banking.auth.requests.RegisterRequest;
import com.mynt.banking.auth.requests.SignUpRequest;
import com.mynt.banking.auth.requests.ValidateKycRequest;
import com.mynt.banking.auth.responses.AuthenticationResponse;
import com.mynt.banking.auth.responses.SDKResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final KYCService kycService;

    @PostMapping(value = "/onfidoSdk", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SDKResponse> onfidoSdk(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(kycService.getOnfidoSDK(request)) ;
    }

    @PostMapping(value = "/validateKyc", consumes = {"application/json", "text/plain"})
    public ResponseEntity<SDKResponse> validateKyc(@RequestBody ValidateKycRequest request) throws IOException {
        return ResponseEntity.ok(kycService.validateKyc(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        AuthenticationResponse response = service.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = service.authenticate(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(HttpServletRequest request) {
        return ResponseEntity.ok(service.refreshToken(request));
    }
}
