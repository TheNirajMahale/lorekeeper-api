package com.lorekeeper.lorekeeper_api.repository;

import com.lorekeeper.lorekeeper_api.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// Repository for accessing Book entities
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE b.openLibraryEditionId = :openLibraryEditionId")
    Optional<Book> findByOpenLibraryEditionId(@Param("openLibraryEditionId") String openLibraryEditionId);

}
