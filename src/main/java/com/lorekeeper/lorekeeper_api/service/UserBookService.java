package com.lorekeeper.lorekeeper_api.service;

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

// Service for managing user-specific book tracking
@Service
public class UserBookService {

    private final UserBookRepository userBookRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final BookService bookService;

    public UserBookService(UserBookRepository userBookRepository, UserRepository userRepository,
                           BookRepository bookRepository, BookService bookService) {
        this.userBookRepository = userBookRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.bookService = bookService;
    }

    public List<UserBookResponseDTO> getUserBooks(Long userId, ReadStatus status, String query) {
        List<UserBook> entries;

        if (status != null) {
            entries = userBookRepository.findByUserIdAndStatus(userId, status);
        } else {
            entries = userBookRepository.findByUserId(userId);
        }

        // Apply text search filter (case-insensitive match on book title or author)
        if (query != null && !query.isBlank()) {
            String lowerQuery = query.toLowerCase();
            entries = entries.stream()
                    .filter(ub -> ub.getBook().getTitle().toLowerCase().contains(lowerQuery)
                            || ub.getBook().getAuthor().toLowerCase().contains(lowerQuery))
                    .collect(Collectors.toList());
        }

        return entries.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public UserBookResponseDTO getLibraryEntry(Long userId, Long libraryEntryId) {
        UserBook userBook = findByIdAndVerifyOwnership(userId, libraryEntryId);
        return mapToDTO(userBook);
    }

    public UserBookResponseDTO trackBook(Long userId, UserBookRequestDTO dto) {
        // Validate User
        User user = userRepository.findById(userId)
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

        // Fix 5: validate page/chapter bounds during initial tracking
        if (dto.getCurrentPage() != null) {
            if (book.getTotalPages() != null && dto.getCurrentPage() > book.getTotalPages()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "currentPage (" + dto.getCurrentPage() + ") cannot exceed totalPages (" + book.getTotalPages() + ")");
            }
            userBook.setCurrentPage(dto.getCurrentPage());
        }
        if (dto.getCurrentChapter() != null) {
            if (book.getTotalChapters() != null && dto.getCurrentChapter() > book.getTotalChapters()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "currentChapter (" + dto.getCurrentChapter() + ") cannot exceed totalChapters (" + book.getTotalChapters() + ")");
            }
            userBook.setCurrentChapter(dto.getCurrentChapter());
        }

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

    public UserBookResponseDTO updateTrackedBook(Long userId, Long libraryEntryId, UserBookUpdateDTO dto) {
        UserBook userBook = findByIdAndVerifyOwnership(userId, libraryEntryId);

        if (dto.getStatus() != null) {
            userBook.setStatus(dto.getStatus());
            // Fix 2: set startedAt when status transitions to READING
            if (dto.getStatus() == ReadStatus.READING && userBook.getStartedAt() == null) {
                userBook.setStartedAt(LocalDateTime.now());
            }
            if (dto.getStatus() == ReadStatus.COMPLETED && userBook.getCompletedAt() == null) {
                userBook.setCompletedAt(LocalDateTime.now());
                // Also set startedAt if somehow it was never set
                if (userBook.getStartedAt() == null) {
                    userBook.setStartedAt(LocalDateTime.now());
                }
            }
        }

        // Fix 5: validate page/chapter bounds before accepting updates
        Book book = userBook.getBook();
        if (dto.getCurrentPage() != null) {
            if (book.getTotalPages() != null && dto.getCurrentPage() > book.getTotalPages()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "currentPage (" + dto.getCurrentPage() + ") cannot exceed totalPages (" + book.getTotalPages() + ")");
            }
            userBook.setCurrentPage(dto.getCurrentPage());
        }
        if (dto.getCurrentChapter() != null) {
            if (book.getTotalChapters() != null && dto.getCurrentChapter() > book.getTotalChapters()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "currentChapter (" + dto.getCurrentChapter() + ") cannot exceed totalChapters (" + book.getTotalChapters() + ")");
            }
            userBook.setCurrentChapter(dto.getCurrentChapter());
        }
        if (dto.getRating() != null) userBook.setRating(dto.getRating());
        if (dto.getIsFavorite() != null) userBook.setFavorite(dto.getIsFavorite());

        UserBook updated = userBookRepository.save(userBook);
        return mapToDTO(updated);
    }

    public void stopTrackingBook(Long userId, Long libraryEntryId) {
        // Verify ownership before deleting
        findByIdAndVerifyOwnership(userId, libraryEntryId);
        userBookRepository.deleteById(libraryEntryId);
    }

    /**
     * Shared helper: find a library entry by ID and verify it belongs to the given user.
     * Throws 404 if entry doesn't exist, 403 if it belongs to a different user.
     */
    private UserBook findByIdAndVerifyOwnership(Long userId, Long libraryEntryId) {
        UserBook userBook = userBookRepository.findById(libraryEntryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Library entry not found"));
        if (!userBook.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have access to this library entry");
        }
        return userBook;
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

        // Reuse BookService's shared mapper instead of duplicating 9 setter calls
        dto.setBook(bookService.mapToDTO(userBook.getBook()));
        return dto;
    }
}
