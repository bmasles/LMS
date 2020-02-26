package com.smoothstack.lms.common.repository;

import com.smoothstack.lms.common.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorCommonRepository extends JpaRepository<Author, Long> {

    List<Author> findAllByAuthorNameIgnoreCase(String authorName);
    Optional<Author> findFirstByAuthorNameIgnoreCase(String authorName);

    default Optional<Author> findByAuthorName(String authorName) {
        return findFirstByAuthorNameIgnoreCase(authorName);
    }

}

