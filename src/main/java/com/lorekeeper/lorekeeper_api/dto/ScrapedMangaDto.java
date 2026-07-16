package com.lorekeeper.lorekeeper_api.dto;

import java.util.List;

public class ScrapedMangaDto {
    private String title;
    private String author;
    private String status;
    private List<String> chapters;
    private String html;

    public ScrapedMangaDto() {
    }

    public ScrapedMangaDto(String title, String author, String status, List<String> chapters, String html) {
        this.title = title;
        this.author = author;
        this.status = status;
        this.chapters = chapters;
        this.html = html;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getChapters() {
        return chapters;
    }

    public void setChapters(List<String> chapters) {
        this.chapters = chapters;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
}
