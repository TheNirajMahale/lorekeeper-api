package com.lorekeeper.lorekeeper_api.dto;

public class OpenLibraryEditionDTO {
    private String editionId;
    private String title;
    private String publishDate;
    private Integer numberOfPages;
    private String coverImageUrl;

    public String getEditionId() { return editionId; }
    public void setEditionId(String editionId) { this.editionId = editionId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getPublishDate() { return publishDate; }
    public void setPublishDate(String publishDate) { this.publishDate = publishDate; }
    public Integer getNumberOfPages() { return numberOfPages; }
    public void setNumberOfPages(Integer numberOfPages) { this.numberOfPages = numberOfPages; }
    public String getCoverImageUrl() { return coverImageUrl; }
    public void setCoverImageUrl(String coverImageUrl) { this.coverImageUrl = coverImageUrl; }
}
