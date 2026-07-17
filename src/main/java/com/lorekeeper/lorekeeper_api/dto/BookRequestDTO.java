package com.lorekeeper.lorekeeper_api.dto;

import com.lorekeeper.lorekeeper_api.entity.BookFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BookRequestDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    private String summary;
    private String coverImageUrl;

    @NotNull
    private BookFormat format;

    @Min(value = 0, message = "Total pages cannot be negative")
    private Integer totalPages;
    
    @Min(value = 0, message = "Total chapters cannot be negative")
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
