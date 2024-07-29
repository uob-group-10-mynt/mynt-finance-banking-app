package com.mynt.banking.auth;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.mynt.banking.auth.requests.AuthenticationRequest;
import com.mynt.banking.auth.requests.RegisterRequest;
import com.mynt.banking.auth.responses.AuthenticationResponse;
import com.mynt.banking.user.User;
import com.mynt.banking.user.UserRepository;
import com.mynt.banking.util.exceptions.authentication.KycException;
import com.mynt.banking.util.exceptions.registration.RegistrationException;
import com.mynt.banking.util.exceptions.registration.UserAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenService tokenService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    public AuthenticationServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUserAlreadyExists() {
        // Arrange
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setPassword("password");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));

        // Act & Assert
        assertThrows(UserAlreadyExistsException.class, () -> {
            authenticationService.register(request);
        });

        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    public void testRegisterExceptionDuringRegistration() {
        // Arrange
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setPassword("password");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        doThrow(new RuntimeException("Database error")).when(userRepository).save(any(User.class));

        // Act & Assert
        assertThrows(RegistrationException.class, () -> {
            authenticationService.register(request);
        });

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testAuthenticateSuccess() {
        // Arrange
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("test@example.com");
        request.setPassword("password");

        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(userRepository.getKycStatus(anyString())).thenReturn(Optional.of("approved"));
        when(tokenService.generateToken(any(User.class))).thenReturn("jwtToken");
        when(tokenService.generateRefreshToken(any(User.class))).thenReturn("refreshToken");

        // Act
        AuthenticationResponse response = authenticationService.authenticate(request);

        // Assert
        assertNotNull(response);
        assertEquals("jwtToken", response.getAccessToken());
        assertEquals("refreshToken", response.getRefreshToken());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    public void testAuthenticateUserNotFound() {
        // Arrange
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("nonexistent@example.com");
        request.setPassword("password");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> authenticationService.authenticate(request));
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    public void testAuthenticateKycStatusNotFound() {
        // Arrange
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("test@example.com");
        request.setPassword("password");

        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(userRepository.getKycStatus(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(KycException.KycStatusNotFoundException.class, () -> authenticationService.authenticate(request));
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    public void testAuthenticateKycNotApproved() {
        // Arrange
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("test@example.com");
        request.setPassword("password");

        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(userRepository.getKycStatus(anyString())).thenReturn(Optional.of("pending"));

        // Act & Assert
        assertThrows(KycException.KycNotApprovedException.class, () -> authenticationService.authenticate(request));
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    public void testAuthenticateFailure() {
        // Arrange
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("test@example.com");
        request.setPassword("wrongpassword");

        doThrow(new BadCredentialsException("Bad credentials")).when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> authenticationService.authenticate(request));
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}
