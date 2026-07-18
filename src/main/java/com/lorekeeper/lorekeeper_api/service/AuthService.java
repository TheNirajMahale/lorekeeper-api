package com.lorekeeper.lorekeeper_api.service;

import com.lorekeeper.lorekeeper_api.dto.AuthResponseDTO;
import com.lorekeeper.lorekeeper_api.dto.LoginRequestDTO;
import com.lorekeeper.lorekeeper_api.dto.RegisterRequestDTO;
import com.lorekeeper.lorekeeper_api.dto.UserResponseDTO;
import com.lorekeeper.lorekeeper_api.entity.AuthProvider;
import com.lorekeeper.lorekeeper_api.entity.User;
import com.lorekeeper.lorekeeper_api.repository.UserRepository;
import com.lorekeeper.lorekeeper_api.security.JwtService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.lorekeeper.lorekeeper_api.dto.GoogleLoginRequestDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;

// Service for handling user registration, login, and JWT token issuance
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Value("${google.client.id}")
    private String googleClientId;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponseDTO register(RegisterRequestDTO request) {
        // Check if email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already registered");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setAuthProvider(AuthProvider.LOCAL);
        // createdAt is handled by @CreationTimestamp in User entity

        User savedUser = userRepository.save(user);

        String jwtToken = jwtService.generateToken(savedUser.getId(), savedUser.getEmail());
        return new AuthResponseDTO(jwtToken, mapToUserResponseDTO(savedUser));
    }

    public AuthResponseDTO login(LoginRequestDTO request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password"));

        // Only allow LOCAL users to login with password
        if (user.getAuthProvider() != AuthProvider.LOCAL) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please login with " + user.getAuthProvider());
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }

        String jwtToken = jwtService.generateToken(user.getId(), user.getEmail());
        return new AuthResponseDTO(jwtToken, mapToUserResponseDTO(user));
    }

    public AuthResponseDTO loginWithGoogle(GoogleLoginRequestDTO request) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(),
                    new GsonFactory())
                    .setAudience(Collections.singletonList(googleClientId))
                    .build();

            GoogleIdToken idToken = verifier.verify(request.getIdToken());
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                // Get profile information from payload
                String email = payload.getEmail();
                String providerId = payload.getSubject();

                User user = userRepository.findByEmail(email).orElse(null);

                if (user == null) {
                    // Register new user
                    user = new User();
                    user.setEmail(email);
                    user.setAuthProvider(AuthProvider.GOOGLE);
                    user.setProviderId(providerId);
                    user = userRepository.save(user);
                } else {
                    // User exists. Check if they signed up with Google.
                    if (user.getAuthProvider() != AuthProvider.GOOGLE) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                "Email already registered with password. Please login normally.");
                    }
                }

                String jwtToken = jwtService.generateToken(user.getId(), user.getEmail());
                return new AuthResponseDTO(jwtToken, mapToUserResponseDTO(user));
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Google ID token");
            }
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Failed to verify Google token: " + e.getMessage());
        }
    }

    private UserResponseDTO mapToUserResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getAuthProvider(),
                user.getCreatedAt());
    }
}
