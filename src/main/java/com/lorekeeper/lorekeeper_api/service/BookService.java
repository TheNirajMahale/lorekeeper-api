package com.lorekeeper.lorekeeper_api.service;

import com.lorekeeper.lorekeeper_api.dto.BookRequestDTO;
import com.lorekeeper.lorekeeper_api.dto.BookResponseDTO;
import com.lorekeeper.lorekeeper_api.entity.Book;
import com.lorekeeper.lorekeeper_api.entity.User;
import com.lorekeeper.lorekeeper_api.repository.BookRepository;
import com.lorekeeper.lorekeeper_api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public BookService(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public List<BookResponseDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public BookResponseDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found with id: " + id));
        return mapToDTO(book);
    }

    public BookResponseDTO createBook(Long userId, BookRequestDTO requestDTO) {
        // Deduplication: if this edition already exists in our catalog, return the existing one
        // instead of creating a duplicate row. See LoreKeeper.md Section 6, Point 1.
        if (requestDTO.getOpenLibraryEditionId() != null) {
            var existing = bookRepository.findByOpenLibraryEditionId(requestDTO.getOpenLibraryEditionId());
            if (existing.isPresent()) {
                return mapToDTO(existing.get());
            }
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Book book = new Book();
        updateEntityFromDTO(book, requestDTO);
        book.setAddedByUser(user);
        Book savedBook = bookRepository.save(book);
        return mapToDTO(savedBook);
    }

    public BookResponseDTO updateBook(Long id, BookRequestDTO requestDTO) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found with id: " + id));
        
        updateEntityFromDTO(book, requestDTO);
        Book updatedBook = bookRepository.save(book);
        return mapToDTO(updatedBook);
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }

    private void updateEntityFromDTO(Book book, BookRequestDTO dto) {
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setSummary(dto.getSummary());
        book.setCoverImageUrl(dto.getCoverImageUrl());
        book.setFormat(dto.getFormat());
        book.setTotalPages(dto.getTotalPages());
        book.setTotalChapters(dto.getTotalChapters());
        book.setOpenLibraryWorkId(dto.getOpenLibraryWorkId());
        book.setOpenLibraryEditionId(dto.getOpenLibraryEditionId());
    }

    /**
     * Shared mapping method — used by both BookService and UserBookService
     * to avoid duplicating Book-to-DTO conversion logic.
     */
    public BookResponseDTO mapToDTO(Book book) {
        BookResponseDTO dto = new BookResponseDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setSummary(book.getSummary());
        dto.setCoverImageUrl(book.getCoverImageUrl());
        dto.setFormat(book.getFormat());
        dto.setTotalPages(book.getTotalPages());
        dto.setTotalChapters(book.getTotalChapters());
        dto.setOpenLibraryWorkId(book.getOpenLibraryWorkId());
        dto.setOpenLibraryEditionId(book.getOpenLibraryEditionId());
        return dto;
    }
}
