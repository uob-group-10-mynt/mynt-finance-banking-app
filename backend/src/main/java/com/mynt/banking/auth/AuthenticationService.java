package com.mynt.banking.auth;

import com.mynt.banking.auth.requests.AuthenticationRequest;
import com.mynt.banking.auth.requests.RegisterRequest;
import com.mynt.banking.auth.responses.AuthenticationResponse;
import com.mynt.banking.currency_cloud.repo.CurrencyCloudEntity;
import com.mynt.banking.currency_cloud.repo.CurrencyCloudRepository;
import com.mynt.banking.user.User;
import com.mynt.banking.user.UserRepository;
import com.mynt.banking.util.exceptions.currency_cloud.CurrencyCloudException;
import com.mynt.banking.util.exceptions.registration.RegistrationException;
import com.mynt.banking.util.exceptions.registration.UserAlreadyExistsException;
import com.mynt.banking.util.exceptions.authentication.KycException;
import com.mynt.banking.util.exceptions.authentication.TokenException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;


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
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // Step 2: Check KYC status
        var kycStatus = userRepository.getKycStatus(request.getEmail())
                .orElseThrow(() -> new KycException.KycStatusNotFoundException("KYC status not found for email: " + request.getEmail()));

        if (!"approved".equalsIgnoreCase(kycStatus)) {
            throw new KycException.KycNotApprovedException("User's status is not approved for login");
        }

        // Step 3: Fetch user and create MyntUserDetails
//        User user = userRepository.findByEmail(request.getEmail())
//                .orElseThrow(() -> new RuntimeException("User should exist at this point, but was not found"));
//        String uuid = currencyCloudRepository.findByUser(user)
//                .map(CurrencyCloudEntity::getUuid)
//                .orElseThrow(() -> new RuntimeException("User should have an associated currency cloud uuid, but was not found"));
//        MyntUserDetails userDetails = new MyntUserDetails(user.getEmail(), uuid, user.getRole());

        // Step 4: Generate tokens
        MyntUserDetails userDetails = (MyntUserDetails) userDetailsService.loadUserByUsername(request.getEmail());
        String accessToken = tokenService.generateToken(userDetails);
        String refreshToken = tokenService.generateRefreshToken(userDetails);

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
            var user = (MyntUserDetails) authentication.getPrincipal();
            var accessToken = tokenService.generateToken(user);
            var newRefreshToken = tokenService.generateRefreshToken(user);

            return AuthenticationResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(newRefreshToken)
                    .build();
        } catch (Exception e) {
            throw new TokenException.TokenRefreshException("Error occurred during token refresh", e);
        }
    }
}
