package com.smoothstack.lms.borrowermicroservice.service;

import com.smoothstack.lms.borrowermicroservice.Debug;
import com.smoothstack.lms.borrowermicroservice.common.model.Author;
import com.smoothstack.lms.borrowermicroservice.common.model.Book;
import com.smoothstack.lms.borrowermicroservice.common.model.Genre;
import com.smoothstack.lms.borrowermicroservice.common.model.Publisher;
import com.smoothstack.lms.borrowermicroservice.common.repository.AuthorRepository;
import com.smoothstack.lms.borrowermicroservice.common.repository.BookRepository;
import com.smoothstack.lms.borrowermicroservice.common.repository.GenreRepository;
import com.smoothstack.lms.borrowermicroservice.common.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BookServices {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    PublisherRepository publisherRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    GenreRepository genreRepository;

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
        book.getBookAuthorCollection().add(author);

        bookRepository.save(book);

        publisher.getPublisherBookCollection().add(book);
        publisherRepository.save(publisher);

        author.getAuthorBookCollection().add(book);
        authorRepository.save(author);

        Debug.println(book.toString());

        Optional<Genre> genreChecked = genreRepository.findFirstByGenreNameIgnoreCase(genre.getGenreName());
        if (!genreChecked.isPresent()) {
            genreRepository.save(genre);
        } else {
            genre = genreChecked.get();
        }

        genre.getGenreBookCollection().add(book);

        genreRepository.save(genre);

        bookRepository.flush();
        publisherRepository.flush();
        authorRepository.flush();
        genreRepository.flush();

        Debug.println("=== END ===");
        return book;
    }



}
