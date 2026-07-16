package com.lorekeeper.lorekeeper_api.dto;

import com.lorekeeper.lorekeeper_api.entity.BookFormat;

public class BookResponseDTO {

    private Long id;
    private String title;
    private String author;
    private String summary;
    private String coverImageUrl;
    private BookFormat format;
    private Integer totalPages;
    private Integer totalChapters;
    private String openLibraryWorkId;
    private String openLibraryEditionId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getCoverImageUrl() { return coverImageUrl; }
    public void setCoverImageUrl(String coverImageUrl) { this.coverImageUrl = coverImageUrl; }

    public BookFormat getFormat() { return format; }
    public void setFormat(BookFormat format) { this.format = format; }

    public Integer getTotalPages() { return totalPages; }
    public void setTotalPages(Integer totalPages) { this.totalPages = totalPages; }

    public Integer getTotalChapters() { return totalChapters; }
    public void setTotalChapters(Integer totalChapters) { this.totalChapters = totalChapters; }

    public String getOpenLibraryWorkId() { return openLibraryWorkId; }
    public void setOpenLibraryWorkId(String openLibraryWorkId) { this.openLibraryWorkId = openLibraryWorkId; }

    public String getOpenLibraryEditionId() { return openLibraryEditionId; }
    public void setOpenLibraryEditionId(String openLibraryEditionId) { this.openLibraryEditionId = openLibraryEditionId; }
}
