package com.lorekeeper.lorekeeper_api.controller;

import com.lorekeeper.lorekeeper_api.dto.OpenLibraryEditionDTO;
import com.lorekeeper.lorekeeper_api.dto.OpenLibraryWorkDTO;
import com.lorekeeper.lorekeeper_api.service.OpenLibraryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/open-library")
public class OpenLibraryController {

    private final OpenLibraryService openLibraryService;

    public OpenLibraryController(OpenLibraryService openLibraryService) {
        this.openLibraryService = openLibraryService;
    }

    @GetMapping("/works")
    public List<OpenLibraryWorkDTO> searchWorks(@RequestParam String title) {
        return openLibraryService.searchWorks(title);
    }

    @GetMapping("/works/{workId}/editions")
    public List<OpenLibraryEditionDTO> getEditions(@PathVariable String workId) {
        return openLibraryService.getEditionsForWork(workId);
    }

    @GetMapping("/editions/{editionId}")
    public OpenLibraryEditionDTO getEditionDetail(@PathVariable String editionId) {
        OpenLibraryEditionDTO edition = openLibraryService.getEditionDetail(editionId);
        if (edition == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Edition not found on Open Library");
        }
        return edition;
    }
}
