package com.lorekeeper.lorekeeper_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

// Represents a user's personal tracking state for a specific book/edition
@Entity
@Table(name = "user_books",
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "book_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The user tracking the book
    @NotNull(message = "User is required")
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // The book being tracked
    @NotNull(message = "Book is required")
    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @NotNull(message = "Reading status is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReadStatus status;

    private Integer currentPage;

    private Integer currentChapter;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer rating;

    @Builder.Default
    @Column(nullable = false)
    private boolean isFavorite = false;

    private LocalDateTime startedAt;

    private LocalDateTime completedAt;
}
