package com.lorekeeper.lorekeeper_api.dto.openlibrary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OLEditionsResponse {
    private List<OLEdition> entries;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OLEdition {
        private String key;
        private String title;
        @JsonProperty("publish_date")
        private String publishDate;
    }
}
