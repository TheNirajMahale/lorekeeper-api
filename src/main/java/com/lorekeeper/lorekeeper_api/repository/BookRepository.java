package com.lorekeeper.lorekeeper_api.repository;

import com.lorekeeper.lorekeeper_api.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
