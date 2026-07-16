package com.lorekeeper.lorekeeper_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users") // 'user' is often a reserved keyword in SQL, so 'users' is safer
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Column(unique = true, nullable = false)
    private String email;

    // Optional because Google auth won't provide a password
    private String passwordHash;

    @NotNull(message = "Auth provider is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthProvider authProvider;

    // The unique ID provided by Google if they logged in via Google
    private String providerId;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    // Constructors
    public User() {}

    public User(String email, String passwordHash, AuthProvider authProvider) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.authProvider = authProvider;
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

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public AuthProvider getAuthProvider() {
        return authProvider;
    }

    public void setAuthProvider(AuthProvider authProvider) {
        this.authProvider = authProvider;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
