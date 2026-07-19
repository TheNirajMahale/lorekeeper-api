package com.lorekeeper.lorekeeper_api.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

// Utility for fetching current user context securely from SecurityContext
@Component
public class SecurityUtils {

    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            String userIdStr = null;
            
            // Spring Security typically stores a UserDetails object here
            if (authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.UserDetails) {
                userIdStr = ((org.springframework.security.core.userdetails.UserDetails) authentication.getPrincipal()).getUsername();
            } else if (authentication.getPrincipal() instanceof String) {
                // Fallback just in case it was stored directly as a string
                userIdStr = (String) authentication.getPrincipal();
            }

            if (userIdStr != null) {
                try {
                    return Long.parseLong(userIdStr);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }
        return null;
    }
}
