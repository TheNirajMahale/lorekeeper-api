package com.lorekeeper.lorekeeper_api.controller;

import com.lorekeeper.lorekeeper_api.dto.UserResponseDTO;
import com.lorekeeper.lorekeeper_api.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserResponseDTO getMyProfile(Principal principal) {
        Long userId = Long.valueOf(principal.getName());
        return userService.getUserProfile(userId);
    }
}
