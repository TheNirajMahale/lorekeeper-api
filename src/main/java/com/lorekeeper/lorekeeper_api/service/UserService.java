package com.lorekeeper.lorekeeper_api.service;

import com.lorekeeper.lorekeeper_api.dto.UserResponseDTO;
import com.lorekeeper.lorekeeper_api.entity.User;
import com.lorekeeper.lorekeeper_api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDTO getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return new UserResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getAuthProvider(),
                user.getCreatedAt()
        );
    }
}
