package com.lorekeeper.lorekeeper_api.dto;

import com.lorekeeper.lorekeeper_api.entity.ReadStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class UserBookUpdateDTO {

    private ReadStatus status;
    @Min(value = 0, message = "Current page cannot be negative")
    private Integer currentPage;
    
    @Min(value = 0, message = "Current chapter cannot be negative")
    private Integer currentChapter;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer rating;

    private Boolean isFavorite;

    public ReadStatus getStatus() {
        return status;
    }

    public void setStatus(ReadStatus status) {
        this.status = status;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getCurrentChapter() {
        return currentChapter;
    }

    public void setCurrentChapter(Integer currentChapter) {
        this.currentChapter = currentChapter;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(Boolean favorite) {
        isFavorite = favorite;
    }
}
