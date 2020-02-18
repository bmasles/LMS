package com.smoothstack.lms.borrowermicroservice.repository;

import com.smoothstack.lms.borrowermicroservice.database.sql.CachedOne;
import com.smoothstack.lms.borrowermicroservice.model.Book;
import com.smoothstack.lms.borrowermicroservice.persistance.CrudRepository;
import com.smoothstack.lms.borrowermicroservice.persistance.CrudRepositoryFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookRepositoryTest {

    @Test
    void cacheOfIdMustCacheABook() {
        CrudRepository bookRepository = CrudRepositoryFactory.getRepository(Book.class);

        CachedOne<Book> aBook = bookRepository.cacheOfId(10);

        assertEquals(null, aBook.get().orElse(new Book()), "Not A Book");
    }

}