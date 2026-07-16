package com.lorekeeper.lorekeeper_api.dto.openlibrary;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class OLEditionsResponse {
    private List<OLEdition> entries;
    public List<OLEdition> getEntries() { return entries; }
    public void setEntries(List<OLEdition> entries) { this.entries = entries; }

    public static class OLEdition {
        private String key;
        private String title;
        @JsonProperty("publish_date")
        private String publishDate;

        public String getKey() { return key; }
        public void setKey(String key) { this.key = key; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getPublishDate() { return publishDate; }
        public void setPublishDate(String publishDate) { this.publishDate = publishDate; }
    }
}
