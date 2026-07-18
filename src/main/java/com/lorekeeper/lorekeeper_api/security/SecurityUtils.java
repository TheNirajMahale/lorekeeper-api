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
            String principalStr = authentication.getPrincipal().toString();
            try {
                return Long.parseLong(principalStr);
            } catch (NumberFormatException e) {
                // If it's not a number, it might be anonymousUser
                return null;
            }
        }
        return null;
    }
}
