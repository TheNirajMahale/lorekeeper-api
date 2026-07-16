package com.lorekeeper.lorekeeper_api.dto.openlibrary;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class OLEditionDetail {
    private String key;
    private String title;
    @JsonProperty("number_of_pages")
    private Integer numberOfPages;
    @JsonProperty("publish_date")
    private String publishDate;
    private List<Long> covers;

    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Integer getNumberOfPages() { return numberOfPages; }
    public void setNumberOfPages(Integer numberOfPages) { this.numberOfPages = numberOfPages; }
    public String getPublishDate() { return publishDate; }
    public void setPublishDate(String publishDate) { this.publishDate = publishDate; }
    public List<Long> getCovers() { return covers; }
    public void setCovers(List<Long> covers) { this.covers = covers; }
}
