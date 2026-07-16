package com.lorekeeper.lorekeeper_api.dto;

import jakarta.validation.constraints.NotBlank;

public class BookRequestDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    private String summary;
    private String coverImageUrl;

    @NotBlank
    private String format;

    private Integer totalPages;
    private Integer totalChapters;
    private String openLibraryWorkId;
    private String openLibraryEditionId;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getCoverImageUrl() { return coverImageUrl; }
    public void setCoverImageUrl(String coverImageUrl) { this.coverImageUrl = coverImageUrl; }

    public String getFormat() { return format; }
    public void setFormat(String format) { this.format = format; }

    public Integer getTotalPages() { return totalPages; }
    public void setTotalPages(Integer totalPages) { this.totalPages = totalPages; }

    public Integer getTotalChapters() { return totalChapters; }
    public void setTotalChapters(Integer totalChapters) { this.totalChapters = totalChapters; }

    public String getOpenLibraryWorkId() { return openLibraryWorkId; }
    public void setOpenLibraryWorkId(String openLibraryWorkId) { this.openLibraryWorkId = openLibraryWorkId; }

    public String getOpenLibraryEditionId() { return openLibraryEditionId; }
    public void setOpenLibraryEditionId(String openLibraryEditionId) { this.openLibraryEditionId = openLibraryEditionId; }
}
