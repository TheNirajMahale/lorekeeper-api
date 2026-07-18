package com.lorekeeper.lorekeeper_api.controller;

import com.lorekeeper.lorekeeper_api.dto.UserBookRequestDTO;
import com.lorekeeper.lorekeeper_api.dto.UserBookResponseDTO;
import com.lorekeeper.lorekeeper_api.dto.UserBookUpdateDTO;
import com.lorekeeper.lorekeeper_api.entity.ReadStatus;
import com.lorekeeper.lorekeeper_api.service.UserBookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.lorekeeper.lorekeeper_api.security.SecurityUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

// Controller for managing the user's personal tracking library
@RestController
@RequestMapping("/users/me/library")
public class UserBookController {

    private final UserBookService userBookService;
    private final SecurityUtils securityUtils;

    public UserBookController(UserBookService userBookService, SecurityUtils securityUtils) {
        this.userBookService = userBookService;
        this.securityUtils = securityUtils;
    }

    private Long getUserId() {
        Long userId = securityUtils.getCurrentUserId();
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }
        return userId;
    }

    @GetMapping
    public List<UserBookResponseDTO> getUserBooks(
            @RequestParam(required = false) ReadStatus status,
            @RequestParam(required = false) String q) {
        return userBookService.getUserBooks(getUserId(), status, q);
    }

    @GetMapping("/{libraryEntryId}")
    public UserBookResponseDTO getLibraryEntry(@PathVariable Long libraryEntryId) {
        return userBookService.getLibraryEntry(getUserId(), libraryEntryId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserBookResponseDTO trackBook(@Valid @RequestBody UserBookRequestDTO dto) {
        return userBookService.trackBook(getUserId(), dto);
    }

    @PatchMapping("/{libraryEntryId}")
    public UserBookResponseDTO updateTrackedBook(@PathVariable Long libraryEntryId, @Valid @RequestBody UserBookUpdateDTO dto) {
        return userBookService.updateTrackedBook(getUserId(), libraryEntryId, dto);
    }

    @DeleteMapping("/{libraryEntryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void stopTrackingBook(@PathVariable Long libraryEntryId) {
        userBookService.stopTrackingBook(getUserId(), libraryEntryId);
    }
}
