package com.mynt.banking.auth;

import com.mynt.banking.auth.requests.AuthenticationRequest;
import com.mynt.banking.auth.requests.RegisterRequest;
import com.mynt.banking.auth.responses.AuthenticationResponse;
import com.mynt.banking.user.User;
import com.mynt.banking.user.UserContextService;
import com.mynt.banking.user.UserRepository;
import com.mynt.banking.util.exceptions.registration.RegistrationException;
import com.mynt.banking.util.exceptions.registration.UserAlreadyExistsException;
import com.mynt.banking.util.exceptions.authentication.KycException;
import com.mynt.banking.util.exceptions.authentication.TokenException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;


    public void register(@NotNull RegisterRequest request) {
        // Check if user already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with this email already exists.");
        }

        // Create and save new user
        try {
            var user = User.builder()
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .dob(request.getDob())
                    .address(request.getAddress())
                    .phone_number(request.getPhoneNumber())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(request.getRole())
                    .build();

            userRepository.save(user);

        } catch (Exception e) {
            throw new RegistrationException("Error occurred during registration", e);
        }
    }

    public AuthenticationResponse authenticate(@NotNull AuthenticationRequest request) {
        // Step 1: Authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Step 2: Check KYC status
        var kycStatus = userRepository.getKycStatus(request.getEmail())
                .orElseThrow(() -> new KycException.KycStatusNotFoundException("KYC status not found for email: " + request.getEmail()));

        if (!"approved".equalsIgnoreCase(kycStatus)) {
            throw new KycException.KycNotApprovedException("User's status is not approved for login");
        }

        // Step 3: Generate tokens
        String accessToken = tokenService.generateToken();
        String refreshToken = tokenService.generateRefreshToken();

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse refreshToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if authentication is null or not authenticated
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new TokenException.TokenValidationException("No authentication credentials found");
        }

        // Extract the username from the authenticated user context
        String userEmail = authentication.getName();

        // Ensure that the username is not null or empty
        if (userEmail == null || userEmail.isEmpty()) {
            throw new TokenException.TokenValidationException("User email not found in authentication context");
        }

        // Generate new access and refresh tokens
        try {
            var accessToken = tokenService.generateToken();
            var newRefreshToken = tokenService.generateRefreshToken();

            return AuthenticationResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(newRefreshToken)
                    .build();
        } catch (Exception e) {
            throw new TokenException.TokenRefreshException("Error occurred during token refresh", e);
        }
    }
}
