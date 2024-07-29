package com.mynt.banking.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LogoutHandlerImpl implements LogoutHandler {

    private final TokenService tokenService;

    @Override
    @RequestMapping(value = "/api/v1/auth/logout", method = RequestMethod.POST)
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // Extract the Authorization header
        final String authHeader = request.getHeader("Authorization");

        // Check if the header is present and starts with "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Extract the JWT token from the header
            String jwt = authHeader.substring(7);

            // Extract username from JWT (not strictly necessary for logout)
            String userEmail = tokenService.extractUsername(jwt);

            // Clear the security context
            if (userEmail != null) {
                SecurityContextHolder.clearContext();
            }
        }
    }
}
