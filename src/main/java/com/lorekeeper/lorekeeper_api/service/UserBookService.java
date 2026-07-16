package com.lorekeeper.lorekeeper_api.service;

import com.lorekeeper.lorekeeper_api.dto.BookResponseDTO;
import com.lorekeeper.lorekeeper_api.dto.UserBookRequestDTO;
import com.lorekeeper.lorekeeper_api.dto.UserBookResponseDTO;
import com.lorekeeper.lorekeeper_api.dto.UserBookUpdateDTO;
import com.lorekeeper.lorekeeper_api.entity.Book;
import com.lorekeeper.lorekeeper_api.entity.ReadStatus;
import com.lorekeeper.lorekeeper_api.entity.User;
import com.lorekeeper.lorekeeper_api.entity.UserBook;
import com.lorekeeper.lorekeeper_api.repository.BookRepository;
import com.lorekeeper.lorekeeper_api.repository.UserBookRepository;
import com.lorekeeper.lorekeeper_api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserBookService {

    private final UserBookRepository userBookRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public UserBookService(UserBookRepository userBookRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.userBookRepository = userBookRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public List<UserBookResponseDTO> getUserBooks(Long userId) {
        return userBookRepository.findByUserId(userId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public UserBookResponseDTO trackBook(UserBookRequestDTO dto) {
        // Validate User
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // Validate Book
        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        // Check for duplicates
        if (userBookRepository.findByUserIdAndBookId(user.getId(), book.getId()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User is already tracking this book");
        }

        UserBook userBook = new UserBook();
        userBook.setUser(user);
        userBook.setBook(book);
        userBook.setStatus(dto.getStatus());
        userBook.setCurrentPage(dto.getCurrentPage());
        userBook.setCurrentChapter(dto.getCurrentChapter());
        userBook.setRating(dto.getRating());
        userBook.setFavorite(dto.isFavorite());

        if (dto.getStatus() == ReadStatus.READING || dto.getStatus() == ReadStatus.COMPLETED) {
            userBook.setStartedAt(LocalDateTime.now());
        }
        if (dto.getStatus() == ReadStatus.COMPLETED) {
            userBook.setCompletedAt(LocalDateTime.now());
        }

        UserBook saved = userBookRepository.save(userBook);
        return mapToDTO(saved);
    }

    public UserBookResponseDTO updateTrackedBook(Long id, UserBookUpdateDTO dto) {
        UserBook userBook = userBookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tracked book not found"));

        if (dto.getStatus() != null) {
            userBook.setStatus(dto.getStatus());
            if (dto.getStatus() == ReadStatus.COMPLETED && userBook.getCompletedAt() == null) {
                userBook.setCompletedAt(LocalDateTime.now());
            }
        }
        if (dto.getCurrentPage() != null) userBook.setCurrentPage(dto.getCurrentPage());
        if (dto.getCurrentChapter() != null) userBook.setCurrentChapter(dto.getCurrentChapter());
        if (dto.getRating() != null) userBook.setRating(dto.getRating());
        if (dto.getIsFavorite() != null) userBook.setFavorite(dto.getIsFavorite());

        UserBook updated = userBookRepository.save(userBook);
        return mapToDTO(updated);
    }

    public void stopTrackingBook(Long id) {
        if (!userBookRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tracked book not found");
        }
        userBookRepository.deleteById(id);
    }

    private UserBookResponseDTO mapToDTO(UserBook userBook) {
        UserBookResponseDTO dto = new UserBookResponseDTO();
        dto.setId(userBook.getId());
        dto.setUserId(userBook.getUser().getId());
        dto.setStatus(userBook.getStatus());
        dto.setCurrentPage(userBook.getCurrentPage());
        dto.setCurrentChapter(userBook.getCurrentChapter());
        dto.setRating(userBook.getRating());
        dto.setFavorite(userBook.isFavorite());
        dto.setStartedAt(userBook.getStartedAt());
        dto.setCompletedAt(userBook.getCompletedAt());

        BookResponseDTO bookDTO = new BookResponseDTO();
        Book book = userBook.getBook();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setFormat(book.getFormat());
        bookDTO.setCoverImageUrl(book.getCoverImageUrl());
        bookDTO.setTotalPages(book.getTotalPages());
        bookDTO.setTotalChapters(book.getTotalChapters());
        bookDTO.setOpenLibraryWorkId(book.getOpenLibraryWorkId());
        bookDTO.setOpenLibraryEditionId(book.getOpenLibraryEditionId());
        
        dto.setBook(bookDTO);
        return dto;
    }
}
