package com.lorekeeper.lorekeeper_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.lorekeeper.lorekeeper_api.entity.ReadStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

// DTO for UserBookRequestDTO
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBookRequestDTO {



    @NotNull(message = "Book ID is required")
    private Long bookId;

    @NotNull(message = "Reading status is required")
    private ReadStatus status;

    @Min(value = 0, message = "Current page cannot be negative")
    private Integer currentPage;

    @Min(value = 0, message = "Current chapter cannot be negative")
    private Integer currentChapter;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer rating;

    @Builder.Default
    private boolean isFavorite = false;
}