package com.lorekeeper.lorekeeper_api.dto;

import java.util.List;

public class OpenLibraryWorkDTO {
    private String workId;
    private String title;
    private List<String> authorNames;
    private Integer firstPublishYear;
    private Integer editionCount;
    private Integer numberOfPagesMedian;

    public String getWorkId() { return workId; }
    public void setWorkId(String workId) { this.workId = workId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public List<String> getAuthorNames() { return authorNames; }
    public void setAuthorNames(List<String> authorNames) { this.authorNames = authorNames; }
    public Integer getFirstPublishYear() { return firstPublishYear; }
    public void setFirstPublishYear(Integer firstPublishYear) { this.firstPublishYear = firstPublishYear; }
    public Integer getEditionCount() { return editionCount; }
    public void setEditionCount(Integer editionCount) { this.editionCount = editionCount; }
    public Integer getNumberOfPagesMedian() { return numberOfPagesMedian; }
    public void setNumberOfPagesMedian(Integer numberOfPagesMedian) { this.numberOfPagesMedian = numberOfPagesMedian; }
}
