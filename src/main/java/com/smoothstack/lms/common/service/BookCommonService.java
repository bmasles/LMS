package com.smoothstack.lms.common.service;

import com.smoothstack.lms.common.exception.DependencyException;
import com.smoothstack.lms.common.model.Book;
import com.smoothstack.lms.common.repository.BookCommonRepository;
import com.smoothstack.lms.common.repository.CopiesCommonRepository;
import com.smoothstack.lms.common.repository.LoansCommonRepository;
import com.smoothstack.lms.common.repository.RepositoryAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.Validator;

public class BookCommonService implements CommonService<Book, Long> {

    private BookCommonRepository authorCommonRepository;

    private Validator validator;

    @Autowired
    public final void setBookCommonRepository(BookCommonRepository authorCommonRepository) {
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
    public JpaRepository<Book, Long> getJpaRepository() {
        return authorCommonRepository;
    }

    @Override
    public void afterSave(Book book) {
        book.getBookAuthorSet().forEach(author -> {
            author.getAuthorBookSet().add(book);
            RepositoryAdapter.getAuthorRepository().save(author);
        });

        book.getPublisher().getPublisherBookSet().add(book);
        RepositoryAdapter.getPublisherRepository().save(book.getPublisher());

        book.getBookGenreSet().forEach(genre -> {
            genre.getGenreBookSet().add(book);
            RepositoryAdapter.getGenreRepository().save(genre);
        });

    }

    @Override
    public boolean beforeDelete(Book book) {

        if (((LoansCommonRepository) RepositoryAdapter.getLoansRepository()).existsByBook(book)) {
            throw new DependencyException("Cannot delete book, must return all book before deletion.");
        }

        ((CopiesCommonRepository) RepositoryAdapter.getCopiesRepository()).deleteAllByBook(book);

        book.getBookAuthorSet().forEach(author -> {
            author.getAuthorBookSet().remove(book);
            RepositoryAdapter.getAuthorRepository().save(author);
        });
        book.getBookAuthorSet().clear();

        book.getPublisher().getPublisherBookSet().remove(book);
        RepositoryAdapter.getPublisherRepository().save(book.getPublisher());
        book.setPublisher(null);

        book.getBookGenreSet().forEach(genre -> {
            genre.getGenreBookSet().remove(book);
            RepositoryAdapter.getGenreRepository().save(genre);
        });
        book.getBookGenreSet().clear();

        RepositoryAdapter.getBookRepository().save(book);

        return true;
    }
}
