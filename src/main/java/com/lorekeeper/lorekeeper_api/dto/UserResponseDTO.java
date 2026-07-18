package com.lorekeeper.lorekeeper_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.lorekeeper.lorekeeper_api.entity.AuthProvider;
import java.time.LocalDateTime;

// DTO for UserResponseDTO
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {
    private Long id;
    private String email;
    private AuthProvider authProvider;
    private LocalDateTime createdAt;
}