package com.mynt.banking.auth;

import com.mynt.banking.auth.requests.AuthenticationRequest;
import com.mynt.banking.auth.requests.RegisterRequest;
import com.mynt.banking.auth.requests.SignUpRequest;
import com.mynt.banking.auth.requests.ValidateKycRequest;
import com.mynt.banking.auth.responses.AuthenticationResponse;
import com.mynt.banking.auth.responses.SDKResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final KYCService kycService;

    @PostMapping(value = "/onfidoSdk", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SDKResponse> onfidoSdk(@RequestBody SignUpRequest request) throws URISyntaxException, IOException, InterruptedException {
        return ResponseEntity.ok(kycService.getOnfidoSDK(request)) ;
    }

    @PostMapping(value = "/validateKyc", consumes = {"application/json", "text/plain"})
    public ResponseEntity<SDKResponse> validateKyc(@RequestBody ValidateKycRequest request) throws URISyntaxException, IOException, InterruptedException {
        return ResponseEntity.ok(kycService.validateKyc(request));
    }

    @PostMapping(value = "/register", consumes = {"application/json", "text/plain"})
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegisterRequest request) throws URISyntaxException, IOException, InterruptedException {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        service.refreshToken(request, response);
    }
}
