package com.mynt.banking.auth;

import com.mynt.banking.auth.requests.AuthenticationRequest;
import com.mynt.banking.auth.requests.RegisterRequest;
import com.mynt.banking.auth.responses.AuthenticationResponse;
import com.mynt.banking.user.User;
import com.mynt.banking.user.UserRepository;
import com.mynt.banking.util.exceptions.KycNotApprovedException;
import com.mynt.banking.util.exceptions.RegistrationException;
import com.mynt.banking.util.exceptions.UserAlreadyExistsException;
import com.mynt.banking.util.exceptions.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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


    public AuthenticationResponse register(@NotNull RegisterRequest request) {
        try {
            // Check if user already exists
            if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                throw new UserAlreadyExistsException("User with this email already exists.");
            }

            // Create and save new user
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

            // Generate tokens
            var accessToken = tokenService.generateToken(user);
            var refreshToken = tokenService.generateRefreshToken(user);

            return AuthenticationResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();

        } catch (Exception e) {
            throw new RegistrationException("Error occurred during registration", e);
        }
    }

    public AuthenticationResponse authenticate(@NotNull AuthenticationRequest request) {
        try {
            // Authenticate user
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword())
            );

            // Fetch user
            var user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new UserNotFoundException("User not found with email: " + request.getEmail()));

            // Check KYC status
            var kycStatus = userRepository.getKycStatus(user.getEmail())
                    .orElseThrow(() -> new UserNotFoundException("KYC status not found for email: " + request.getEmail()));

            if (!"approved".equalsIgnoreCase(kycStatus)) {
                throw new KycNotApprovedException("User is not approved for login");
            }

            // Generate tokens
            var accessToken = tokenService.generateToken(user);
            var refreshToken = tokenService.generateRefreshToken(user);

            return AuthenticationResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();

        } catch (AuthenticationCredentialsNotFoundException e) {
            throw new AuthenticationCredentialsNotFoundException("Invalid credentials", e);
        } catch (Exception e) {
            throw new AuthenticationException("Error occurred during authentication", e) {};
        }
    }


    public AuthenticationResponse refreshToken() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // Check if authentication is null or not authenticated
            if (authentication == null || !authentication.isAuthenticated()) {
                throw new AuthenticationCredentialsNotFoundException("No authentication credentials found");
            }

            // Extract the username from the authenticated user context
            String userEmail = authentication.getName();

            // Fetch user details from the repository
            var user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new UserNotFoundException("User not found with email: " + userEmail));

            // Generate new access and refresh tokens
            var accessToken = tokenService.generateToken(user);
            var newRefreshToken = tokenService.generateRefreshToken(user);

            return AuthenticationResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(newRefreshToken)
                    .build();
        } catch (UserNotFoundException e) {
            throw new AuthenticationException("User not found during token refresh", e) {};
        } catch (AuthenticationCredentialsNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new AuthenticationException("Error occurred during token refresh", e) {};
        }
    }
}
