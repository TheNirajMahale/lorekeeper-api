package com.lorekeeper.lorekeeper_api.dto;

import com.lorekeeper.lorekeeper_api.entity.ReadStatus;
import java.time.LocalDateTime;

public class UserBookResponseDTO {

    private Long id;
    private Long userId;
    private BookResponseDTO book;
    private ReadStatus status;
    private Integer currentPage;
    private Integer currentChapter;
    private Integer rating;
    private boolean isFavorite;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;

    // Constructors
    public UserBookResponseDTO() {}

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BookResponseDTO getBook() {
        return book;
    }

    public void setBook(BookResponseDTO book) {
        this.book = book;
    }

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

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
}
