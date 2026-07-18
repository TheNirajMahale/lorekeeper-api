package com.lorekeeper.lorekeeper_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.lorekeeper.lorekeeper_api.entity.ReadStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

// DTO for UserBookUpdateDTO
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBookUpdateDTO {

    private ReadStatus status;
    @Min(value = 0, message = "Current page cannot be negative")
    private Integer currentPage;
    
    @Min(value = 0, message = "Current chapter cannot be negative")
    private Integer currentChapter;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer rating;

    private Boolean isFavorite;

    public ReadStatus getStatus() {
        return status;
    }
}