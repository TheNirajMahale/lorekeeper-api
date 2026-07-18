package com.lorekeeper.lorekeeper_api.controller;

import com.lorekeeper.lorekeeper_api.dto.AuthResponseDTO;
import com.lorekeeper.lorekeeper_api.dto.LoginRequestDTO;
import com.lorekeeper.lorekeeper_api.dto.RegisterRequestDTO;
import com.lorekeeper.lorekeeper_api.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

// Controller handling user authentication and registration
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponseDTO register(@Valid @RequestBody RegisterRequestDTO request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@Valid @RequestBody LoginRequestDTO request) {
        return authService.login(request);
    }

    @PostMapping("/google")
    public AuthResponseDTO loginWithGoogle(@Valid @RequestBody com.lorekeeper.lorekeeper_api.dto.GoogleLoginRequestDTO request) {
        return authService.loginWithGoogle(request);
    }
}
