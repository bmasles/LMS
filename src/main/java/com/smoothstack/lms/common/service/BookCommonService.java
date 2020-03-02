package com.smoothstack.lms.common.service;

import com.smoothstack.lms.common.exception.DependencyException;
import com.smoothstack.lms.common.model.Book;
import com.smoothstack.lms.common.repository.BookCommonRepository;
import com.smoothstack.lms.common.repository.RepositoryAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Validator;

@Service
public abstract class BookCommonService implements CommonService<Book, Long> {

    @PersistenceContext
    private EntityManager entityManager;

    private BookCommonRepository bookCommonRepository;

    private Validator validator;

    @Autowired
    public final void setBookCommonRepository(BookCommonRepository authorCommonRepository) {
        this.bookCommonRepository = authorCommonRepository;
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
        return bookCommonRepository;
    }

    @Override
    public boolean beforeSave(Book book) {
        book.getBookAuthorSet().forEach(author -> {
            if (author != null
                    && author.getAuthorId() != 0
                    && !entityManager.contains(author)) {

                RepositoryAdapter.getAuthorRepository().save(author);
            }
        });

        // DETACHED and not TRANSIENT ?
        if (book.getPublisher() != null
                    && book.getPublisher().getPublisherId() != 0
                    && !entityManager.contains(book.getPublisher())) {

                RepositoryAdapter.getPublisherRepository().save(book.getPublisher());
        }

        book.getBookGenreSet().forEach(genre -> {
            if (genre != null && genre.getGenreId() != 0 && !entityManager.contains(genre)) {

                RepositoryAdapter.getGenreRepository().save(genre);
            }
        });

        return true;
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

        if (RepositoryAdapter.getLoansRepository().existsByBook(book)) {
            throw new DependencyException("Cannot delete book, must return all book before deletion.");
        }

        RepositoryAdapter.getCopiesRepository().deleteAllByBook(book);

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
