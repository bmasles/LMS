package com.smoothstack.lms.common.repository;

import com.smoothstack.lms.common.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookCommonRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByBookTitleIgnoreCase(String bookTitle);

    Optional<Book> findFirstByBookTitleIgnoreCase(String bookTitle);

    int countAllByBookTitleIgnoreCase(String bookTitle);
}
