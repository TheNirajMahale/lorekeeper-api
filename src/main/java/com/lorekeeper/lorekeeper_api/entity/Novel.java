package com.lorekeeper.lorekeeper_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity                          // Tells Spring this class maps to a database table
@Table(name = "novels")          // The actual table name in Postgres
public class Novel {

    @Id                                                    // Marks this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // Postgres auto-increments this, we never set it manually
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Author is required")
    private String author;

    // TEXT-like fields — plain String works fine for Postgres, Hibernate maps it to a text/varchar column
    @NotBlank(message = "Summary is required")
    private String summary;

    // Just a URL string pointing to an image (e.g. cover art) — not the actual image file itself
    private String image;

    // e.g. "21220" — null if the novel was added manually
    private String royalRoadId;

    // --- Getters and setters below: required so Jackson (JSON conversion) and JPA (database mapping)
    // can read/write these private fields from outside the class ---

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

    public String getRoyalRoadId() {
        return royalRoadId;
    }

    public void setRoyalRoadId(String royalRoadId) {
        this.royalRoadId = royalRoadId;
    }

    // Note: no "chapters" field here on purpose. Chapter -> Novel is a @ManyToOne relationship
    // (see Chapter.java), so Novel doesn't need to store its chapters directly — they're looked up
    // via ChapterRepository.findByNovelId(novelId) instead. Keeps this class simple and avoids
    // loading a huge nested chapter list every time you just want basic novel info.
}