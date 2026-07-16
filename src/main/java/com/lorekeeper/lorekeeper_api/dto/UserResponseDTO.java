package com.lorekeeper.lorekeeper_api.dto;

import com.lorekeeper.lorekeeper_api.entity.AuthProvider;
import java.time.LocalDateTime;

public class UserResponseDTO {
    private Long id;
    private String email;
    private AuthProvider authProvider;
    private LocalDateTime createdAt;

    // Constructors
    public UserResponseDTO() {}

    public UserResponseDTO(Long id, String email, AuthProvider authProvider, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.authProvider = authProvider;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AuthProvider getAuthProvider() {
        return authProvider;
    }

    public void setAuthProvider(AuthProvider authProvider) {
        this.authProvider = authProvider;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
