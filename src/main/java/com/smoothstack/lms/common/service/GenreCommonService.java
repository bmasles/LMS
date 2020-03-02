package com.smoothstack.lms.common.service;

import com.smoothstack.lms.common.exception.DependencyException;
import com.smoothstack.lms.common.model.Book;
import com.smoothstack.lms.common.model.Genre;
import com.smoothstack.lms.common.repository.GenreCommonRepository;
import com.smoothstack.lms.common.repository.RepositoryAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.Validator;

public abstract class GenreCommonService implements CommonService<Genre, Long> {


    private GenreCommonRepository authorCommonRepository;

    private Validator validator;

    @Autowired
    public final void setGenreCommonRepository(GenreCommonRepository authorCommonRepository) {
        this.authorCommonRepository = authorCommonRepository;
    }

    @Autowired
    public void setValidator(@Qualifier("DefaultValidator") Validator validator) {
        this.validator = validator;
    }

    @Override
    public Validator getValidator() {
        return validator;
    }

    @Override
    public JpaRepository<Genre, Long> getJpaRepository() {
        return authorCommonRepository;
    }

    @Override
    public boolean beforeDelete(Genre genre) {
        if (genre.getGenreBookSet().size() > 0)
            throw new DependencyException("Book set in genre must be empty before deletion.");

        return true;
    }

    public Book removeBookFromGenre(Genre genre, Book book) {

        genre.getGenreBookSet().remove(book);

        book.getBookGenreSet().remove(genre);

        RepositoryAdapter.getBookRepository().save(book);

        RepositoryAdapter.getGenreRepository().save(genre);

        return book;
    }
}

