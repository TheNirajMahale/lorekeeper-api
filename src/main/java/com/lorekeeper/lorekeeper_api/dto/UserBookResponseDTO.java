package com.lorekeeper.lorekeeper_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.lorekeeper.lorekeeper_api.entity.ReadStatus;
import java.time.LocalDateTime;

// DTO for UserBookResponseDTO
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBookResponseDTO {

    private Long id;
    private Long userId;
    private BookResponseDTO book;
    private ReadStatus status;
    private Integer currentPage;
    private Integer currentChapter;
    private Integer rating;
    private boolean isFavorite;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
}