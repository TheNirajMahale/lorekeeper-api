package com.lorekeeper.lorekeeper_api.controller;

import com.lorekeeper.lorekeeper_api.dto.UserBookRequestDTO;
import com.lorekeeper.lorekeeper_api.dto.UserBookResponseDTO;
import com.lorekeeper.lorekeeper_api.dto.UserBookUpdateDTO;
import com.lorekeeper.lorekeeper_api.entity.ReadStatus;
import com.lorekeeper.lorekeeper_api.service.UserBookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users/me/library")
public class UserBookController {

    private final UserBookService userBookService;

    public UserBookController(UserBookService userBookService) {
        this.userBookService = userBookService;
    }

    @GetMapping
    public List<UserBookResponseDTO> getUserBooks(
            Principal principal,
            @RequestParam(required = false) ReadStatus status,
            @RequestParam(required = false) String q) {
        Long userId = Long.valueOf(principal.getName());
        return userBookService.getUserBooks(userId, status, q);
    }

    @GetMapping("/{libraryEntryId}")
    public UserBookResponseDTO getLibraryEntry(Principal principal, @PathVariable Long libraryEntryId) {
        Long userId = Long.valueOf(principal.getName());
        return userBookService.getLibraryEntry(userId, libraryEntryId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserBookResponseDTO trackBook(Principal principal, @Valid @RequestBody UserBookRequestDTO dto) {
        Long userId = Long.valueOf(principal.getName());
        return userBookService.trackBook(userId, dto);
    }

    @PatchMapping("/{libraryEntryId}")
    public UserBookResponseDTO updateTrackedBook(Principal principal, @PathVariable Long libraryEntryId, @Valid @RequestBody UserBookUpdateDTO dto) {
        Long userId = Long.valueOf(principal.getName());
        return userBookService.updateTrackedBook(userId, libraryEntryId, dto);
    }

    @DeleteMapping("/{libraryEntryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void stopTrackingBook(Principal principal, @PathVariable Long libraryEntryId) {
        Long userId = Long.valueOf(principal.getName());
        userBookService.stopTrackingBook(userId, libraryEntryId);
    }
}
