package com.mynt.banking.user;

import com.mynt.banking.auth.JwtUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserContextService {

    /**
     * Retrieves the authenticated user's details from the SecurityContext.
     *
     * @return JwtUserDetails of the authenticated user
     * @throws SecurityException if no user is authenticated or principal is not of type JwtUserDetails
     */
    public JwtUserDetails getCurrentUserDetails() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof JwtUserDetails)) {
            throw new SecurityException("Authentication not available or principal is not of type JwtUserDetails");
        }

        return (JwtUserDetails) authentication.getPrincipal();
    }

    /**
     * Retrieves the UUID of the current authenticated user.
     *
     * @return UUID of the authenticated user
     * @throws SecurityException if no user is authenticated or principal is not of type JwtUserDetails
     */
    public String getCurrentUserUuid() {
        return getCurrentUserDetails().getUuid();
    }

    /**
     * Retrieves the username of the current authenticated user.
     *
     * @return Username of the authenticated user
     * @throws SecurityException if no user is authenticated or principal is not of type JwtUserDetails
     */
    public String getCurrentUsername() {
        return getCurrentUserDetails().getUsername();
    }
}
