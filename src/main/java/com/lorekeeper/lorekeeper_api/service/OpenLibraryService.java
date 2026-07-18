package com.lorekeeper.lorekeeper_api.service;

import com.lorekeeper.lorekeeper_api.dto.OpenLibraryEditionDTO;
import com.lorekeeper.lorekeeper_api.dto.OpenLibraryWorkDTO;
import com.lorekeeper.lorekeeper_api.dto.openlibrary.OLEditionDetail;
import com.lorekeeper.lorekeeper_api.dto.openlibrary.OLEditionsResponse;
import com.lorekeeper.lorekeeper_api.dto.openlibrary.OLSearchResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

// Service for interacting with the external Open Library API
@Service
public class OpenLibraryService {

    private final RestTemplate restTemplate;
    private final String contactEmail;

    public OpenLibraryService(
            RestTemplate restTemplate,
            @Value("${lorekeeper.openlibrary.contact-email:noreply@example.com}") String contactEmail) {
        this.restTemplate = restTemplate;
        this.contactEmail = contactEmail;
    }

    private HttpEntity<String> createEntityWithUserAgent() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.USER_AGENT, "LoreKeeper (" + contactEmail + ")");
        return new HttpEntity<>(headers);
    }

    public List<OpenLibraryWorkDTO> searchWorks(String query) {
        String url = UriComponentsBuilder.fromUriString("https://openlibrary.org/search.json")
                .queryParam("title", query)
                .queryParam("fields", "key,title,author_name,first_publish_year,edition_count,number_of_pages_median")
                .toUriString();

        OLSearchResponse response = restTemplate.exchange(
                url, HttpMethod.GET, createEntityWithUserAgent(), OLSearchResponse.class).getBody();

        if (response == null || response.getDocs() == null) {
            return Collections.emptyList();
        }

        return response.getDocs().stream().map(doc -> {
            OpenLibraryWorkDTO dto = new OpenLibraryWorkDTO();
            // OpenLibrary returns keys like "/works/OL123W". We strip the "/works/" part.
            dto.setWorkId(extractId(doc.getKey()));
            dto.setTitle(doc.getTitle());
            dto.setAuthorNames(doc.getAuthorName());
            dto.setFirstPublishYear(doc.getFirstPublishYear());
            dto.setEditionCount(doc.getEditionCount());
            dto.setNumberOfPagesMedian(doc.getNumberOfPagesMedian());
            return dto;
        }).collect(Collectors.toList());
    }

    public List<OpenLibraryEditionDTO> getEditionsForWork(String workId) {
        String url = UriComponentsBuilder.fromUriString("https://openlibrary.org/works/{workId}/editions.json")
                .queryParam("limit", 20)
                .buildAndExpand(workId)
                .toUriString();

        OLEditionsResponse response = restTemplate.exchange(
                url, HttpMethod.GET, createEntityWithUserAgent(), OLEditionsResponse.class).getBody();

        if (response == null || response.getEntries() == null) {
            return Collections.emptyList();
        }

        return response.getEntries().stream().map(entry -> {
            OpenLibraryEditionDTO dto = new OpenLibraryEditionDTO();
            dto.setEditionId(extractId(entry.getKey()));
            dto.setTitle(entry.getTitle());
            dto.setPublishDate(entry.getPublishDate());
            return dto;
        }).collect(Collectors.toList());
    }

    public OpenLibraryEditionDTO getEditionDetail(String editionId) {
        String url = UriComponentsBuilder.fromUriString("https://openlibrary.org/books/{editionId}.json")
                .buildAndExpand(editionId)
                .toUriString();

        OLEditionDetail detail = restTemplate.exchange(
                url, HttpMethod.GET, createEntityWithUserAgent(), OLEditionDetail.class).getBody();

        if (detail == null) {
            return null;
        }

        OpenLibraryEditionDTO dto = new OpenLibraryEditionDTO();
        dto.setEditionId(extractId(detail.getKey()));
        dto.setTitle(detail.getTitle());
        dto.setPublishDate(detail.getPublishDate());
        dto.setNumberOfPages(detail.getNumberOfPages());
        
        if (detail.getCovers() != null && !detail.getCovers().isEmpty()) {
            dto.setCoverImageUrl("https://covers.openlibrary.org/b/id/" + detail.getCovers().get(0) + "-L.jpg");
        }
        
        return dto;
    }

    private String extractId(String key) {
        if (key == null) return null;
        String[] parts = key.split("/");
        return parts[parts.length - 1];
    }
}
