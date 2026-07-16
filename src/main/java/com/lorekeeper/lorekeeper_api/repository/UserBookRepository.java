package com.lorekeeper.lorekeeper_api.repository;

import com.lorekeeper.lorekeeper_api.entity.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserBookRepository extends JpaRepository<UserBook, Long> {

    List<UserBook> findByUserId(Long userId);

    Optional<UserBook> findByUserIdAndBookId(Long userId, Long bookId);
    
}
