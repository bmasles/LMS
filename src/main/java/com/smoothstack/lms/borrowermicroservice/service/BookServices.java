package com.smoothstack.lms.borrowermicroservice.service;

import com.smoothstack.lms.common.util.Debug;
import com.smoothstack.lms.common.model.Author;
import com.smoothstack.lms.common.model.Book;
import com.smoothstack.lms.common.model.Genre;
import com.smoothstack.lms.common.model.Publisher;
import com.smoothstack.lms.common.repository.AuthorCommonRepository;
import com.smoothstack.lms.common.repository.BookCommonRepository;
import com.smoothstack.lms.common.repository.GenreCommonRepository;
import com.smoothstack.lms.common.repository.PublisherCommonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BookServices {

    @Autowired
    BookCommonRepository bookRepository;

    @Autowired
    PublisherCommonRepository publisherRepository;

    @Autowired
    AuthorCommonRepository authorRepository;

    @Autowired
    GenreCommonRepository genreRepository;

    @Transactional
    public Book buildAndSave(Book book, Publisher publisher, Author author, Genre genre) {
        Debug.println("=== BEGIN ===");

        Optional<Publisher> publisherChecked = publisherRepository.findFirstByPublisherNameIgnoreCase(publisher.getPublisherName());
        if (!publisherChecked.isPresent()) {
            publisherRepository.save(publisher);
        } else {
            publisher = publisherChecked.get();
        }

        Optional<Author> authorChecked = authorRepository.findFirstByAuthorNameIgnoreCase(author.getAuthorName());
        if (!authorChecked.isPresent()) {
            authorRepository.save(author);
        } else {
            author = authorChecked.get();
        }

        Debug.println(publisher.toString());
        Debug.println(author.toString());

        book.setPublisher(publisher);
        book.getBookAuthorSet().add(author);

        bookRepository.save(book);

        publisher.getPublisherBookSet().add(book);
        publisherRepository.save(publisher);

        author.getAuthorBookSet().add(book);
        authorRepository.save(author);

        Debug.println(book.toString());

        Optional<Genre> genreChecked = genreRepository.findFirstByGenreNameIgnoreCase(genre.getGenreName());
        if (!genreChecked.isPresent()) {
            genreRepository.save(genre);
        } else {
            genre = genreChecked.get();
        }

        genre.getGenreBookSet().add(book);

        genreRepository.save(genre);

        bookRepository.flush();
        publisherRepository.flush();
        authorRepository.flush();
        genreRepository.flush();

        Debug.println("=== END ===");
        return book;
    }



}
