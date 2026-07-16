package com.lorekeeper.lorekeeper_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    private String summary;

    private String coverImageUrl;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookFormat format;

    private Integer totalPages;

    private Integer totalChapters;

    private String openLibraryWorkId;

    private String openLibraryEditionId;

    @ManyToOne
    @JoinColumn(name = "added_by_user_id")
    private User addedByUser;

    public Book() {}

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

    public User getAddedByUser() { return addedByUser; }
    public void setAddedByUser(User addedByUser) { this.addedByUser = addedByUser; }
}
