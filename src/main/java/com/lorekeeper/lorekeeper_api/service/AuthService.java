package com.lorekeeper.lorekeeper_api.service;

import com.lorekeeper.lorekeeper_api.dto.AuthResponseDTO;
import com.lorekeeper.lorekeeper_api.dto.LoginRequestDTO;
import com.lorekeeper.lorekeeper_api.dto.RegisterRequestDTO;
import com.lorekeeper.lorekeeper_api.dto.UserResponseDTO;
import com.lorekeeper.lorekeeper_api.entity.AuthProvider;
import com.lorekeeper.lorekeeper_api.entity.User;
import com.lorekeeper.lorekeeper_api.repository.UserRepository;
import com.lorekeeper.lorekeeper_api.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

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

    private UserResponseDTO mapToUserResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getAuthProvider(),
                user.getCreatedAt()
        );
    }
}
