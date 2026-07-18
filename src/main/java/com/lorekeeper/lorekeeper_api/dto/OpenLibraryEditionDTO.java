package com.lorekeeper.lorekeeper_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO for OpenLibraryEditionDTO
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpenLibraryEditionDTO {
    private String editionId;
    private String title;
    private String publishDate;
    private Integer numberOfPages;
    private String coverImageUrl;
}