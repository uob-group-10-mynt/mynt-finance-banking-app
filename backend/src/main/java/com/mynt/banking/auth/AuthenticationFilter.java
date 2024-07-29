package com.mynt.banking.auth;

import com.mynt.banking.auth.JwtUserDetails;
import com.mynt.banking.util.exceptions.authentication.KycException;
import com.mynt.banking.util.exceptions.authentication.TokenException;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final AuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain
    ) throws ServletException, IOException, java.io.IOException {

        final String authHeader = request.getHeader("Authorization");
        final String accessToken;

        if (!StringUtils.hasLength(authHeader) || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        accessToken = authHeader.substring(7);

        try {
            // Decrypt and extract username from token
            String decryptedToken = tokenService.decryptToken(accessToken);
            String username = tokenService.extractUsername(decryptedToken);

            JwtUserDetails jwtUserDetails = tokenService.extractUserDetails(decryptedToken);

            // Log extracted user details
            log.info("Extracted username: {}", username);
            log.info("Extracted user details: {}", jwtUserDetails);

            // Check if user is not authenticated already
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Validate the token
                if (tokenService.isTokenValid(decryptedToken, jwtUserDetails)) {
                    SecurityContext context = SecurityContextHolder.createEmptyContext();
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            jwtUserDetails,
                            null,
                            jwtUserDetails.getAuthorities()
                    );
                    context.setAuthentication(authentication);
                    SecurityContextHolder.setContext(context);
                    log.info("Authentication set in SecurityContext.");
                } else {
                    log.warn("Invalid token.");
                    authenticationFailureHandler.commence(request, response, new AuthenticationException("Invalid token") {});
                    return;
                }
            }
        } catch (TokenException | KycException ex) {
            log.error("Authentication error: {}", ex.getMessage());
            authenticationFailureHandler.commence(request, response, new AuthenticationException(ex.getMessage()) {});
            return;
        }
        filterChain.doFilter(request, response);
    }
}
