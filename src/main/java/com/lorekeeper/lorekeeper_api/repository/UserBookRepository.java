package com.lorekeeper.lorekeeper_api.repository;

import com.lorekeeper.lorekeeper_api.entity.ReadStatus;
import com.lorekeeper.lorekeeper_api.entity.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// Repository for accessing UserBook (tracking) entities
@Repository
public interface UserBookRepository extends JpaRepository<UserBook, Long> {

    @Query("SELECT ub FROM UserBook ub WHERE ub.user.id = :userId")
    List<UserBook> findByUserId(@Param("userId") Long userId);

    @Query("SELECT ub FROM UserBook ub WHERE ub.user.id = :userId AND ub.status = :status")
    List<UserBook> findByUserIdAndStatus(@Param("userId") Long userId, @Param("status") ReadStatus status);

    @Query("SELECT ub FROM UserBook ub WHERE ub.user.id = :userId AND ub.book.id = :bookId")
    Optional<UserBook> findByUserIdAndBookId(@Param("userId") Long userId, @Param("bookId") Long bookId);
    
}
