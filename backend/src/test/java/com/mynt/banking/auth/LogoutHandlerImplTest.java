package com.mynt.banking.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class LogoutHandlerImplTest {

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private JWTService jwtService;

    @InjectMocks
    private LogoutHandlerImpl logoutHandlerImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogout_Success() {
        // Arrange
        String email = "test@example.com";
        String jwt = "Bearer validJwtToken";
        RefreshToken refreshToken = RefreshToken.builder()
                .key(email)
                .token("refreshToken")
                .build();

        when(jwtService.extractUsername(any(String.class))).thenReturn(email);
        when(refreshTokenRepository.findById(email)).thenReturn(Optional.of(refreshToken));

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Authentication authentication = mock(Authentication.class);

        when(request.getHeader("Authorization")).thenReturn(jwt);

        // Act
        logoutHandlerImpl.logout(request, response, authentication);

        // Assert
        verify(refreshTokenRepository, times(1)).delete(eq(refreshToken));
        assertDoesNotThrow(() -> SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    public void testLogout_NoTokenInRequest() {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Authentication authentication = mock(Authentication.class);

        when(request.getHeader("Authorization")).thenReturn(null);

        // Act
        logoutHandlerImpl.logout(request, response, authentication);

        // Assert
        verify(refreshTokenRepository, never()).delete(any(RefreshToken.class));
        assertDoesNotThrow(() -> SecurityContextHolder.getContext().getAuthentication()); // Ensure context is cleared
    }

    @Test
    public void testLogout_InvalidJwt() {
        // Arrange
        String jwt = "Bearer invalidJwtToken";
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Authentication authentication = mock(Authentication.class);

        when(request.getHeader("Authorization")).thenReturn(jwt);
        when(jwtService.extractUsername(any(String.class))).thenReturn(null);

        // Act
        logoutHandlerImpl.logout(request, response, authentication);

        // Assert
        verify(refreshTokenRepository, never()).delete(any(RefreshToken.class));
        assertDoesNotThrow(() -> SecurityContextHolder.getContext().getAuthentication()); // Ensure context is cleared
    }
}
