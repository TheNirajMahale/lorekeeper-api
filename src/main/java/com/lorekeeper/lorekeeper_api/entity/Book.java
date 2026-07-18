package com.lorekeeper.lorekeeper_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Represents a specific edition of a book, comic, or manga tracked in the catalog
@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    // The abstract Open Library work this edition belongs to
    private String openLibraryWorkId;

    // The specific Open Library edition used to fetch cover/page count
    private String openLibraryEditionId;

    // Who first added this edition to the shared local catalog
    @ManyToOne
    @JoinColumn(name = "added_by_user_id")
    private User addedByUser;
}
