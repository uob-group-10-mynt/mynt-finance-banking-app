package com.mynt.banking.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;

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
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);

            // Save refresh token in Redis
            refreshTokenRepository.save(new RefreshToken(user.getEmail(), refreshToken));

            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
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
            if (!"approved".equalsIgnoreCase(String.valueOf(userRepository.getKycStatus(user.getEmail())))) {
                throw new KycNotApprovedException("User is not approved for login");
            }

            // Generate tokens
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);

            // Update tokens in Redis
            refreshTokenRepository.deleteById(user.getEmail());
            refreshTokenRepository.save(new RefreshToken(user.getEmail(), refreshToken));

            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();

        } catch (AuthenticationCredentialsNotFoundException e) {
            throw new AuthenticationCredentialsNotFoundException("Invalid credentials", e);
        } catch (AuthenticationException e) {
            throw new AuthenticationException("Error occurred during authentication", e) {};
        }
    }

    @SneakyThrows
    public void refreshToken(@NotNull HttpServletRequest request, HttpServletResponse response) {
        try {
            final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Invalid or missing Authorization header");
                return;
            }

            final String refreshToken = authHeader.substring(7);
            final String userEmail = jwtService.extractUsername(refreshToken);

            if (userEmail != null) {
                // Validate the refresh token by querying Redis
                RefreshToken storedToken = refreshTokenRepository.findById(userEmail).orElse(null);

                if (storedToken != null && storedToken.getToken().equals(refreshToken)) {
                    var user = userRepository.findByEmail(userEmail)
                            .orElseThrow(() -> new UserNotFoundException("User not found with email: " + userEmail));

                    if (jwtService.isTokenValid(refreshToken, user)) {
                        var accessToken = jwtService.generateToken(user);
                        var newRefreshToken = jwtService.generateRefreshToken(user);

                        // Update tokens in Redis
                        refreshTokenRepository.deleteById(userEmail);
                        refreshTokenRepository.save(new RefreshToken(userEmail, newRefreshToken));

                        var authResponse = AuthenticationResponse.builder()
                                .accessToken(accessToken)
                                .refreshToken(newRefreshToken)
                                .build();

                        response.setContentType("application/json");
                        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
                    } else {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.getWriter().write("Invalid refresh token");
                    }
                } else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Refresh token not found or mismatched");
                }
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Invalid refresh token");
            }

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error occurred during token refresh");
        }
    }
}
