package com.lorekeeper.lorekeeper_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.lorekeeper.lorekeeper_api.entity.BookFormat;

// DTO for BookResponseDTO
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponseDTO {

    private Long id;
    private String title;
    private String author;
    private String summary;
    private String coverImageUrl;
    private BookFormat format;
    private Integer totalPages;
    private Integer totalChapters;
    private String openLibraryWorkId;
    private String openLibraryEditionId;
}