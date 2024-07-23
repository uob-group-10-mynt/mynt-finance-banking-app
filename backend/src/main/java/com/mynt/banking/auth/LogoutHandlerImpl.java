package com.mynt.banking.auth;

import com.mynt.banking.auth.JWTService;
import com.mynt.banking.auth.RefreshToken;
import com.mynt.banking.auth.RefreshTokenRepository;
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

    private final RefreshTokenRepository refreshTokenRepository;
    private final JWTService jwtService;

    @Override
    @RequestMapping(value = "/api/v1/auth/logout", method = RequestMethod.POST)
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            String userEmail = jwtService.extractUsername(jwt);

            if (userEmail != null) {
                refreshTokenRepository.findById(userEmail).ifPresent(refreshTokenRepository::delete);
                SecurityContextHolder.clearContext();
            }
        }
    }
}
