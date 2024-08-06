package com.mynt.banking.auth;

import com.mynt.banking.auth.requests.AuthenticationRequest;
import com.mynt.banking.auth.requests.RegisterRequest;
import com.mynt.banking.auth.requests.SignUpRequest;
import com.mynt.banking.auth.requests.ValidateKycRequest;
import com.mynt.banking.auth.responses.AuthenticationResponse;
import com.mynt.banking.auth.responses.SDKResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final KycService kycService;

    @PostMapping(value = "/onfidoSdk", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SDKResponse> onfidoSdk(@RequestBody SignUpRequest request) {
        return kycService.getOnfidoSDK(request) ;
    }

    @PostMapping(value = "/validateKyc", consumes = {"application/json", "text/plain"})
    public ResponseEntity<SDKResponse> validateKyc(@RequestBody ValidateKycRequest request) throws IOException {
        return ResponseEntity.ok(kycService.validateKyc(request));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest request) {
        service.register(request);
        return ResponseEntity.ok().build(); // Returns an empty 200 OK response
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = service.authenticate(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh-token")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<AuthenticationResponse> refreshToken() {
        return ResponseEntity.ok(service.refreshToken());
    }
}
