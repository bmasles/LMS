package com.smoothstack.lms.common.repository;

import com.smoothstack.lms.common.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenreCommonRepository extends JpaRepository<Genre, Long> {

    Optional<Genre> findFirstByGenreNameIgnoreCase(String genreName);
}
