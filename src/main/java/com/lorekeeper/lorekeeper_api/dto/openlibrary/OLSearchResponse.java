package com.lorekeeper.lorekeeper_api.dto.openlibrary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

// OpenLibrary Work Search Response
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OLSearchResponse {
    private List<OLSearchResult> docs;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OLSearchResult {
        private String key;
        private String title;

        @JsonProperty("author_name")
        private List<String> authorName;

        @JsonProperty("first_publish_year")
        private Integer firstPublishYear;

        @JsonProperty("edition_count")
        private Integer editionCount;

        @JsonProperty("number_of_pages_median")
        private Integer numberOfPagesMedian;
    }
}
