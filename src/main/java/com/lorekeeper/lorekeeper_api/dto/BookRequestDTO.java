package com.lorekeeper.lorekeeper_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.lorekeeper.lorekeeper_api.entity.BookFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// DTO for BookRequestDTO
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRequestDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    private String summary;
    private String coverImageUrl;

    @NotNull
    private BookFormat format;

    @Min(value = 0, message = "Total pages cannot be negative")
    private Integer totalPages;
    
    @Min(value = 0, message = "Total chapters cannot be negative")
    private Integer totalChapters;
    
    private String openLibraryWorkId;
    private String openLibraryEditionId;
}