package com.smoothstack.lms.borrowermicroservice.persistance;

import com.smoothstack.lms.borrowermicroservice.model.Book;
import com.smoothstack.lms.borrowermicroservice.model.Borrower;
import com.smoothstack.lms.borrowermicroservice.model.Library;
import com.smoothstack.lms.borrowermicroservice.model.Loans;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CrudRepositoryFactoryTest {


    @Autowired
    private CrudRepository<Library> libraryCrudRepository;
    @Autowired
    private CrudRepository<Borrower> borrowerCrudRepository;
    @Autowired
    private CrudRepository<Book> bookCrudRepository;
    @Autowired
    private CrudRepository<Loans> loansCrudRepository;


    @Test
    void bookRepository() {
        Assert.assertNotNull(bookCrudRepository);
    }

    @Test
    void libraryRepository() {
        Assert.assertNotNull(libraryCrudRepository);
    }

    @Test
    void borrowerRepository() {
        Assert.assertNotNull(borrowerCrudRepository);
    }

    @Test
    void loanRepository() {
        Assert.assertNotNull(loansCrudRepository);
    }
}