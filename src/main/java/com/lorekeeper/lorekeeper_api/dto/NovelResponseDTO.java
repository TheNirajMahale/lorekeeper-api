package com.lorekeeper.lorekeeper_api.dto;

// This is what the CLIENT actually sees in JSON responses — deliberately mirrors Novel's
// public-facing fields. Right now it's identical to the entity, but this separation matters
// once Novel gains fields that shouldn't be exposed (e.g. internal scraping metadata later).
public class NovelResponseDTO {

    private Long id;
    private String title;
    private String author;
    private String summary;
    private String image;
    private String royalRoadId;

    public String getRoyalRoadId() {
        return royalRoadId;
    }

    public void setRoyalRoadId(String royalRoadId) {
        this.royalRoadId = royalRoadId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}