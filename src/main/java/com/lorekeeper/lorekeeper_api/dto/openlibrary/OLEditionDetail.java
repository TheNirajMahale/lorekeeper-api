package com.lorekeeper.lorekeeper_api.dto.openlibrary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

// DTO for OLEditionDetail
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OLEditionDetail {
    private String key;
    private String title;
    @JsonProperty("number_of_pages")
    private Integer numberOfPages;
    @JsonProperty("publish_date")
    private String publishDate;
    private List<Long> covers;
}