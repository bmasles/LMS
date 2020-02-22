package com.smoothstack.lms.borrowermicroservice.controller;

import com.smoothstack.lms.borrowermicroservice.model.*;
import com.smoothstack.lms.borrowermicroservice.persistance.CrudRepository;
import com.smoothstack.lms.borrowermicroservice.service.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({
        "/borrowers",
        "/borrower"})
public class BorrowerController {

    private BorrowerService borrowerService;

    private CrudRepository<Library> libraryCrudRepository;

    private CrudRepository<Borrower> borrowerCrudRepository;

    private CrudRepository<Book> bookCrudRepository;

    private CrudRepository<Loans> loansCrudRepository;

    @Autowired
    public void setBorrowerService(BorrowerService borrowerService) {
        this.borrowerService = borrowerService;
    }

    @Autowired
    @Qualifier("LibraryRepository")
    public void setLibraryCrudRepository(CrudRepository<Library> libraryCrudRepository) {
        this.libraryCrudRepository = libraryCrudRepository;
    }

    @Autowired
    @Qualifier("BorrowerRepository")
    public void setBorrowerCrudRepository(CrudRepository<Borrower> borrowerCrudRepository) {
        this.borrowerCrudRepository = borrowerCrudRepository;
    }

    @Autowired
    @Qualifier("BookRepository")
    public void setBookCrudRepository(CrudRepository<Book> bookCrudRepository) {
        this.bookCrudRepository = bookCrudRepository;
    }

    @Autowired
    @Qualifier("LoanRepository")
    public void setLoansCrudRepository(CrudRepository<Loans> loansCrudRepository) {
        this.loansCrudRepository = loansCrudRepository;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = {
                    "/libraries/{libraryId}/books",
                    "/library/{libraryId}/book"
            },
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
            )
    public ResponseEntity<List<Copies>> listBook(@PathVariable("libraryId") int libraryId,
                                                 @RequestParam("minCopies") Optional<Integer> minCopies) {

        Optional<Library> library = libraryCrudRepository.findByIds(libraryId);

        if (library.isPresent()) {

            List<Copies> bookList = borrowerService.getBookListAtLibrary(library.get(), minCopies.orElse(1));

            return new ResponseEntity<>(bookList, HttpStatus.OK);

        } else {

            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = {
                    "/{borrowerId}/books",
                    "/{borrowerId}/book"
            },
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
            )
    public ResponseEntity<List<Loans>> listLoans(@PathVariable("borrowerId") int borrowerId) {

        Optional<Borrower> borrower = borrowerCrudRepository.findByIds(borrowerId);

        if (borrower.isPresent()) {

            List<Loans> bookList = borrowerService.getBookListBorrowedByBorrower(borrower.get());

            return new ResponseEntity<>(bookList, HttpStatus.OK);

        } else {

            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(
            method = RequestMethod.POST,
            path = {
                    "/{borrowerId}/libraries/{libraryId}/books/{bookId}/loan",
                    "/{borrowerId}/library/{libraryId}/book/{bookId}/loan"
            },
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
            )
    public ResponseEntity borrowBook(@PathVariable("borrowerId") int borrowerId,
                              @PathVariable("libraryId") int libraryId,
                              @PathVariable("bookId") int bookId) {

        Optional<Borrower> borrower = borrowerCrudRepository.findByIds(borrowerId);
        Optional<Library> library = libraryCrudRepository.findByIds(libraryId);
        Optional<Book> book = bookCrudRepository.findByIds(bookId);


        if (borrower.isPresent() && library.isPresent() && book.isPresent()) {

            return borrowerService.checkoutBookFromLibraryByBorrower(
                    borrower.get(), library.get(), book.get());
        } else {

            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).build();
        }
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            path = {
                    "/{borrowerId}/libraries/{libraryId}/books/{bookId}/return",
                    "/{borrowerId}/library/{libraryId}/book/{bookId}/return"
            },
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
            )
    public ResponseEntity returnBook(@PathVariable("borrowerId") int borrowerId,
                              @PathVariable("libraryId") int libraryId,
                              @PathVariable("bookId") int bookId) {

        Optional<Borrower> borrower = borrowerCrudRepository.findByIds(borrowerId);
        Optional<Library> library = libraryCrudRepository.findByIds(libraryId);
        Optional<Book> book = bookCrudRepository.findByIds(bookId);

        if (borrower.isPresent() && library.isPresent() && book.isPresent()) {
            Optional<Loans> loans = loansCrudRepository.findByIds(
                    book.get().getId(), library.get().getId(), borrower.get().getId());
            if (loans.isPresent()) {
                return borrowerService.returnBookToLibraryByBorrower(
                        loans.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } else {

            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).build();
        }
    }
}
