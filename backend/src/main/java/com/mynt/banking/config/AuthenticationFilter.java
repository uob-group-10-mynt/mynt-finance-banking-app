package com.mynt.banking.config;

import com.mynt.banking.auth.JwtUserDetails;
import com.mynt.banking.auth.TokenService;
import com.mynt.banking.user.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextChangedEvent;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String accessToken;
        final String username;

        if (!StringUtils.hasLength(authHeader) || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        accessToken = authHeader.substring(7);
        // TODO check the security details/context is set correctly and then access through utils JWE - how to store token detais
        // maybe make a jwtuserdetails service
        // SecurityContextHolder.MODE_GLOBAL
        try {
            // Decrypt and extract username from token
            String decryptedToken = tokenService.decryptToken(accessToken);
            username = tokenService.extractUsername(decryptedToken);

            JwtUserDetails userDetails = tokenService.extractUserDetails(decryptedToken);

            // Check if user is not authenticated already
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Validate the token
                if (tokenService.isTokenValid(decryptedToken, userDetails)) {
                    SecurityContext context = SecurityContextHolder.createEmptyContext();
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    context.setAuthentication(authentication);
                    SecurityContextHolder.setContext(context);
//                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } finally {
            filterChain.doFilter(request, response);
        }
    }
}
