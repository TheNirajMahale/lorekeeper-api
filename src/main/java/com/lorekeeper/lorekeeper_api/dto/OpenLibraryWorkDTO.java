package com.lorekeeper.lorekeeper_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// DTO for OpenLibraryWorkDTO
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpenLibraryWorkDTO {
    private String workId;
    private String title;
    private List<String> authorNames;
    private Integer firstPublishYear;
    private Integer editionCount;
    private Integer numberOfPagesMedian;
}