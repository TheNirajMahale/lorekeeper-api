package com.lorekeeper.lorekeeper_api.service;

import com.lorekeeper.lorekeeper_api.dto.UserBookRequestDTO;
import com.lorekeeper.lorekeeper_api.dto.UserBookResponseDTO;
import com.lorekeeper.lorekeeper_api.entity.Book;
import com.lorekeeper.lorekeeper_api.entity.BookFormat;
import com.lorekeeper.lorekeeper_api.entity.ReadStatus;
import com.lorekeeper.lorekeeper_api.entity.User;
import com.lorekeeper.lorekeeper_api.entity.UserBook;
import com.lorekeeper.lorekeeper_api.repository.BookRepository;
import com.lorekeeper.lorekeeper_api.repository.UserBookRepository;
import com.lorekeeper.lorekeeper_api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserBookServiceTest {

    @Mock
    private UserBookRepository userBookRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;

    private BookService bookService;
    private UserBookService userBookService;

    private User mockUser;
    private Book mockBook;
    private UserBookRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        // BookService is not mocked — we use a real instance so mapToDTO() works
        bookService = new BookService(bookRepository, userRepository);
        userBookService = new UserBookService(userBookRepository, userRepository, bookRepository, bookService);

        mockUser = new User();
        mockUser.setId(1L);

        mockBook = new Book();
        mockBook.setId(100L);
        mockBook.setTitle("Dune");
        mockBook.setFormat(BookFormat.BOOK);

        requestDTO = new UserBookRequestDTO();
        requestDTO.setBookId(100L);
        requestDTO.setStatus(ReadStatus.READING);
    }

    @Test
    void trackBook_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(bookRepository.findById(100L)).thenReturn(Optional.of(mockBook));
        when(userBookRepository.findByUserIdAndBookId(1L, 100L)).thenReturn(Optional.empty());

        UserBook savedMock = new UserBook(mockUser, mockBook, ReadStatus.READING);
        savedMock.setId(5L);
        when(userBookRepository.save(any(UserBook.class))).thenReturn(savedMock);

        UserBookResponseDTO response = userBookService.trackBook(1L, requestDTO);

        assertNotNull(response);
        assertEquals(ReadStatus.READING, response.getStatus());
        assertEquals("Dune", response.getBook().getTitle());
        verify(userBookRepository, times(1)).save(any(UserBook.class));
    }

    @Test
    void trackBook_FailsIfAlreadyTracking() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(bookRepository.findById(100L)).thenReturn(Optional.of(mockBook));
        when(userBookRepository.findByUserIdAndBookId(1L, 100L)).thenReturn(Optional.of(new UserBook()));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userBookService.trackBook(1L, requestDTO);
        });

        assertEquals(409, exception.getStatusCode().value());
        assertTrue(exception.getReason().contains("already tracking"));
        verify(userBookRepository, never()).save(any(UserBook.class));
    }
}
