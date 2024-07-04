package com.mynt.banking.auth;

import com.mynt.banking.user.UserService;
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
    public ResponseEntity<SDKResponceDTO> onfidoSdk(@RequestBody SignUpRequest request) throws URISyntaxException, IOException, InterruptedException {
        return ResponseEntity.ok(kycService.getOnfidoSDK(request)) ;
    }



    //@Valid
    @PostMapping(value = "/validateKyc", consumes = {"application/json", "text/plain"})
    public ResponseEntity<SDKResponceDTO> validateKyc(@RequestBody ValidateKycRequest request) throws URISyntaxException, IOException, InterruptedException {
        return ResponseEntity.ok(kycService.retrieveResults(request));
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
