package com.mynt.banking.auth;

import com.mynt.banking.auth.requests.AuthenticationRequest;
import com.mynt.banking.auth.requests.RegisterRequest;
import com.mynt.banking.auth.responses.AuthenticationResponse;
import com.mynt.banking.user.User;
import com.mynt.banking.user.UserRepository;
import com.mynt.banking.util.exceptions.authentication.KycException;
import com.mynt.banking.util.exceptions.authentication.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    private TokenService tokenService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister() throws Exception {
        // Arrange
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setPassword("password");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(tokenService.generateToken(any(User.class))).thenReturn("jwtToken");
        when(tokenService.generateRefreshToken(any(User.class))).thenReturn("refreshToken");

        // Act
        AuthenticationResponse response = authenticationService.register(request);

        // Assert
        assertNotNull(response);
        assertEquals("jwtToken", response.getAccessToken());
        assertEquals("refreshToken", response.getRefreshToken());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testAuthenticate() throws Exception {
        // Arrange
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("test@example.com");
        request.setPassword("password");

        User user = new User();
        user.setEmail("test@example.com");

        // Mock authentication process
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mock(Authentication.class));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(tokenService.generateToken(any(User.class))).thenReturn("jwtToken");
        when(tokenService.generateRefreshToken(any(User.class))).thenReturn("refreshToken");
        when(tokenService.isTokenValid(anyString(), any(User.class))).thenReturn(true);
        when(userRepository.getKycStatus(anyString())).thenReturn(Optional.of("approved"));

        // Act
        AuthenticationResponse authResponse = authenticationService.authenticate(request);

        // Assert
        assertNotNull(authResponse);
        assertEquals("jwtToken", authResponse.getAccessToken());
        assertEquals("refreshToken", authResponse.getRefreshToken());
    }


    @Test
    public void testRefreshToken() throws Exception {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("test@example.com");
        when(authentication.isAuthenticated()).thenReturn(true);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        // Log security context and authentication
        System.out.println("SecurityContext: " + SecurityContextHolder.getContext());
        System.out.println("Authentication: " + SecurityContextHolder.getContext().getAuthentication());

        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(tokenService.generateToken(any(User.class))).thenReturn("newAccessToken");
        when(tokenService.generateRefreshToken(any(User.class))).thenReturn("newRefreshToken");

        // Act
        AuthenticationResponse authResponse = authenticationService.refreshToken();

        // Assert
        assertNotNull(authResponse, "Response should not be null");
        assertEquals("newAccessToken", authResponse.getAccessToken(), "Access token should be 'newAccessToken'");
        assertEquals("newRefreshToken", authResponse.getRefreshToken(), "Refresh token should be 'newRefreshToken'");
    }



    @Test
    public void testRefreshToken_UserNotFound() {
        // Arrange
        // Create and configure the mock Authentication object
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("test@example.com");
        when(authentication.isAuthenticated()).thenReturn(true); // Ensure the authentication is considered valid

        // Create and configure the mock SecurityContext object
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        // Set the SecurityContextHolder to use the mock SecurityContext
        SecurityContextHolder.setContext(securityContext);

        // Configure the userRepository to simulate user not being found
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(AuthenticationException.class, () -> {
            authenticationService.refreshToken();
        });

        // Check if the exception is caused by UserNotFoundException
        Throwable cause = exception.getCause();
        assertNotNull(cause); // Ensure the cause is not null
        assertInstanceOf(UserNotFoundException.class, cause);
        assertEquals("User not found with email: test@example.com", cause.getMessage());
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
        Exception exception = assertThrows(AuthenticationException.class, () -> {
            authenticationService.authenticate(request);
        });

        // Extract the cause of the AuthenticationException
        Throwable cause = exception.getCause();
        assertNotNull(cause);
        assertInstanceOf(KycException.KycNotApprovedException.class, cause);
        assertEquals("User is not approved for login", cause.getMessage());
    }

    @Test
    public void testAccessTokenExpiration() throws Exception {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // Mock the authorization header to simulate an expired token scenario
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer expiredRefreshToken");

        // Set up SecurityContext with a mocked Authentication
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("test@example.com");
        when(authentication.isAuthenticated()).thenReturn(true); // Ensure the authentication is considered valid
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Create a user with the expected email
        User user = new User();
        user.setEmail("test@example.com");

        // Mock user repository and token service
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(tokenService.isTokenValid(anyString(), any(User.class))).thenReturn(true); // Simulate valid token
        when(tokenService.generateToken(any(User.class))).thenReturn("newAccessToken");
        when(tokenService.generateRefreshToken(any(User.class))).thenReturn("newRefreshToken");

        // Act
        AuthenticationResponse refreshResponse = authenticationService.refreshToken();

        // Assert
        assertNotNull(refreshResponse, "Response should not be null");
        assertEquals("newAccessToken", refreshResponse.getAccessToken(), "Access token should be 'newAccessToken'");
        assertEquals("newRefreshToken", refreshResponse.getRefreshToken(), "Refresh token should be 'newRefreshToken'");
    }


    @Test
    public void testRefreshTokenExpiration() {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);

        // Mock the authorization header
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer expiredRefreshToken");

        // Mock the security context with an authenticated user
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("test@example.com");
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Mock user repository and token service
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));
        when(tokenService.isTokenValid(anyString(), any(User.class))).thenReturn(false); // Simulating expired token

        // Act & Assert
        Exception exception = assertThrows(AuthenticationCredentialsNotFoundException.class, () -> {
            authenticationService.refreshToken();
        });

        String expectedMessage = "No authentication credentials found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testInvalidToken() {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);

        // Mock SecurityContextHolder to return an Authentication with the expected user
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        // Simulate invalid token scenario by not finding the user
        when(securityContext.getAuthentication()).thenReturn(null); // No authentication found
        when(tokenService.extractUsername(anyString())).thenReturn("test@example.com");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(AuthenticationCredentialsNotFoundException.class, () -> {
            authenticationService.refreshToken();
        });

        String expectedMessage = "No authentication credentials found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
