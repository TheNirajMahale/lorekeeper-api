package com.lorekeeper.lorekeeper_api.controller;

import com.lorekeeper.lorekeeper_api.dto.UserBookRequestDTO;
import com.lorekeeper.lorekeeper_api.dto.UserBookResponseDTO;
import com.lorekeeper.lorekeeper_api.dto.UserBookUpdateDTO;
import com.lorekeeper.lorekeeper_api.service.UserBookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/me/library")
public class UserBookController {

    private final UserBookService userBookService;

    public UserBookController(UserBookService userBookService) {
        this.userBookService = userBookService;
    }

    @GetMapping
    public List<UserBookResponseDTO> getUserBooks(@RequestParam Long userId) {
        // Temporarily requires userId as a query parameter until Phase 4 (Authentication)
        // extracts the user identity from the JWT token automatically.
        return userBookService.getUserBooks(userId);
    }

    @GetMapping("/{libraryEntryId}")
    public UserBookResponseDTO getLibraryEntry(@PathVariable Long libraryEntryId) {
        return userBookService.getLibraryEntry(libraryEntryId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserBookResponseDTO trackBook(@Valid @RequestBody UserBookRequestDTO dto) {
        return userBookService.trackBook(dto);
    }

    @PatchMapping("/{libraryEntryId}")
    public UserBookResponseDTO updateTrackedBook(@PathVariable Long libraryEntryId, @Valid @RequestBody UserBookUpdateDTO dto) {
        return userBookService.updateTrackedBook(libraryEntryId, dto);
    }

    @DeleteMapping("/{libraryEntryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void stopTrackingBook(@PathVariable Long libraryEntryId) {
        userBookService.stopTrackingBook(libraryEntryId);
    }
}
