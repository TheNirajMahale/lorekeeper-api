package com.lorekeeper.lorekeeper_api.controller;

import com.lorekeeper.lorekeeper_api.dto.BookRequestDTO;
import com.lorekeeper.lorekeeper_api.dto.BookResponseDTO;
import com.lorekeeper.lorekeeper_api.service.BookService;
import com.lorekeeper.lorekeeper_api.service.OpenLibraryService;
import com.lorekeeper.lorekeeper_api.dto.OpenLibraryWorkDTO;
import com.lorekeeper.lorekeeper_api.dto.OpenLibraryEditionDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final OpenLibraryService openLibraryService;

    // Constructor injection
    public BookController(BookService bookService, OpenLibraryService openLibraryService) {
        this.bookService = bookService;
        this.openLibraryService = openLibraryService;
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
        return bookService.createBook(requestDTO);
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

    @GetMapping("/search")
    public List<OpenLibraryWorkDTO> searchWorks(@RequestParam String title) {
        return openLibraryService.searchWorks(title);
    }

    @GetMapping("/search/{workId}/editions")
    public List<OpenLibraryEditionDTO> getEditions(@PathVariable String workId) {
        return openLibraryService.getEditionsForWork(workId);
    }

    @GetMapping("/search/editions/{editionId}")
    public OpenLibraryEditionDTO getEditionDetail(@PathVariable String editionId) {
        OpenLibraryEditionDTO edition = openLibraryService.getEditionDetail(editionId);
        if (edition == null) {
            throw new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND, "Edition not found on Open Library");
        }
        return edition;
    }
}
