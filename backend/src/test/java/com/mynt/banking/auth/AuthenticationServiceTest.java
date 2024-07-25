package com.mynt.banking.auth;

import com.mynt.banking.auth.requests.AuthenticationRequest;
import com.mynt.banking.auth.requests.RegisterRequest;
import com.mynt.banking.auth.responses.AuthenticationResponse;
import com.mynt.banking.user.User;
import com.mynt.banking.user.UserRepository;
import com.mynt.banking.util.exceptions.KycNotApprovedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.io.PrintWriter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JWTService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister() {
        // Arrange
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setPassword("password");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");
        when(jwtService.generateRefreshToken(any(User.class))).thenReturn("refreshToken");

        // Act
        AuthenticationResponse response = authenticationService.register(request);

        // Assert
        assertNotNull(response);
        assertEquals("jwtToken", response.getAccessToken());
        assertEquals("refreshToken", response.getRefreshToken());
        verify(userRepository, times(1)).save(any(User.class));
        verify(refreshTokenRepository, times(1)).save(any(RefreshToken.class));
    }


    @Test
    public void testAuthenticate() {
        // Arrange
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("test@example.com");
        request.setPassword("password");

        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");
        when(jwtService.generateRefreshToken(any(User.class))).thenReturn("refreshToken");
        when(jwtService.isTokenValid(anyString(), any(User.class))).thenReturn(true);
        when(userRepository.getKycStatus(anyString())).thenReturn(Optional.of("approved"));

        // Act
        AuthenticationResponse response = authenticationService.authenticate(request);

        // Assert
        assertNotNull(response);
        assertEquals("jwtToken", response.getAccessToken());
        assertEquals("refreshToken", response.getRefreshToken());
    }

    @Test
    public void testRefreshToken() throws Exception {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        PrintWriter writer = mock(PrintWriter.class);

        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer oldRefreshToken");
        when(jwtService.extractUsername(anyString())).thenReturn("test@example.com");
        when(jwtService.isTokenValid(anyString(), any(User.class))).thenReturn(true);

        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(refreshTokenRepository.findById(anyString())).thenReturn(Optional.of(RefreshToken.builder()
                .key("test@example.com")
                .token("oldRefreshToken")
                .build()));
        when(jwtService.generateToken(any(User.class))).thenReturn("newAccessToken");
        when(jwtService.generateRefreshToken(any(User.class))).thenReturn("newRefreshToken");

        when(response.getWriter()).thenReturn(writer);

        // Act
        authenticationService.refreshToken(request);

        // Assert
        verify(refreshTokenRepository, times(1)).save(any(RefreshToken.class));
    }


    @Test
    public void testAuthenticate_KycNotApproved() {
        // Arrange
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("test@example.com");
        request.setPassword("password");

        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(userRepository.getKycStatus(anyString())).thenReturn(Optional.of("pending"));

        // Act & Assert
        Exception exception = assertThrows(KycNotApprovedException.class, () -> {
            authenticationService.authenticate(request);
        });

        String expectedMessage = "User is not approved for login";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testAccessTokenExpiration() {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer expiredRefreshToken");
        when(jwtService.extractUsername(anyString())).thenReturn("test@example.com");

        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(refreshTokenRepository.findById(anyString())).thenReturn(Optional.of(RefreshToken.builder()
                .key("test@example.com")
                .token("expiredRefreshToken")
                .build()));
        when(jwtService.isTokenValid(anyString(), any(User.class))).thenReturn(true);
        when(jwtService.generateToken(any(User.class))).thenReturn("newAccessToken");
        when(jwtService.generateRefreshToken(any(User.class))).thenReturn("newRefreshToken");

        // Act
        AuthenticationResponse response = authenticationService.refreshToken(request);

        // Assert
        assertNotNull(response);
        assertEquals("newAccessToken", response.getAccessToken());
        assertEquals("newRefreshToken", response.getRefreshToken());
        verify(refreshTokenRepository, times(1)).save(any(RefreshToken.class));
        verify(refreshTokenRepository, times(1)).deleteById(anyString());
    }

    @Test
    public void testRefreshTokenExpiration() throws Exception {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer expiredRefreshToken");
        when(jwtService.extractUsername(anyString())).thenReturn("test@example.com");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));
        when(refreshTokenRepository.findById(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(AuthenticationCredentialsNotFoundException.class, () -> {
            authenticationService.refreshToken(request);
        });

        String expectedMessage = "Refresh token not found or mismatched";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testInvalidToken() throws Exception {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer invalidToken");
        when(jwtService.extractUsername(anyString())).thenReturn("test@example.com");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));
        when(refreshTokenRepository.findById(anyString())).thenReturn(Optional.of(RefreshToken.builder()
                .key("test@example.com")
                .token("invalidToken")
                .build()));

        // Act & Assert
        Exception exception = assertThrows(AuthenticationCredentialsNotFoundException.class, () -> {
            authenticationService.refreshToken(request);
        });

        String expectedMessage = "Invalid refresh token";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}

