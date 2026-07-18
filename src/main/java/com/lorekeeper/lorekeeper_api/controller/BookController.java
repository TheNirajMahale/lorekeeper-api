package com.lorekeeper.lorekeeper_api.controller;

import com.lorekeeper.lorekeeper_api.dto.BookRequestDTO;
import com.lorekeeper.lorekeeper_api.dto.BookResponseDTO;
import com.lorekeeper.lorekeeper_api.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.lorekeeper.lorekeeper_api.security.SecurityUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

// Controller for managing Book entities (the shared catalog)
@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final SecurityUtils securityUtils;

    public BookController(BookService bookService, SecurityUtils securityUtils) {
        this.bookService = bookService;
        this.securityUtils = securityUtils;
    }

    @GetMapping
    public List<BookResponseDTO> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public BookResponseDTO getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponseDTO createBook(@Valid @RequestBody BookRequestDTO requestDTO) {
        Long userId = securityUtils.getCurrentUserId();
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }
        return bookService.createBook(userId, requestDTO);
    }

    @PutMapping("/{id}")
    public BookResponseDTO updateBook(@PathVariable Long id, @Valid @RequestBody BookRequestDTO requestDTO) {
        return bookService.updateBook(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}
