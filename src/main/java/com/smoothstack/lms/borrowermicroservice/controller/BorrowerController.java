package com.smoothstack.lms.borrowermicroservice.controller;


import com.smoothstack.lms.borrowermicroservice.common.model.*;
import com.smoothstack.lms.borrowermicroservice.common.repository.BookRepository;
import com.smoothstack.lms.borrowermicroservice.common.repository.BorrowerRepository;
import com.smoothstack.lms.borrowermicroservice.common.repository.LibraryBranchRepository;
import com.smoothstack.lms.borrowermicroservice.common.repository.LoansRepository;
import com.smoothstack.lms.borrowermicroservice.common.util.LoopTerminator;
import com.smoothstack.lms.borrowermicroservice.service.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({"/borrowers"})
public class BorrowerController {

    //TODO: Change to setter-based injection

    @Autowired
    private BorrowerService borrowerService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowerRepository borrowerRepository;

    @Autowired
    private LoansRepository loansRepository;

    @Autowired
    private LibraryBranchRepository libraryBranchRepository;

    @Autowired
    private LoopTerminator loopTerminator;

    @Autowired
    public void setBorrowerService(BorrowerService borrowerService) {
        this.borrowerService = borrowerService;
    }



    @RequestMapping(
            method = RequestMethod.GET,
            path = {"/libraries/{libraryId}/books"},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
            )
    public ResponseEntity<List<Copies>> listBook(@PathVariable("libraryId") long libraryId,
                                                 @RequestParam("minCopies") Optional<Integer> minCopies) {

        Optional<LibraryBranch> libraryBranch = libraryBranchRepository.findById(libraryId);

        if (libraryBranch.isPresent()) {

            List<Copies> bookList = borrowerService.getBookListAtLibrary(libraryBranch.get(), minCopies.orElse(1));

            return new ResponseEntity<>(bookList, HttpStatus.OK);

        } else {

            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = {"/{borrowerId}/books:list"},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
            )
    public ResponseEntity<List<Loans>> listLoans(@PathVariable("borrowerId") long borrowerId) {

        Optional<Borrower> borrower = borrowerRepository.findById(borrowerId);

        if (borrower.isPresent()) {

            List<Loans> bookList = borrowerService.getBookListBorrowedByBorrower(borrower.get());

            bookList.forEach(loopTerminator::terminate);

            return new ResponseEntity<>(bookList, HttpStatus.OK);

        } else {

            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(
            method = RequestMethod.POST,
            path = {"/{borrowerId}/libraries/{libraryId}/book:checkout/{bookId}/loan"},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
            )
    public ResponseEntity borrowBook(@PathVariable("borrowerId") long borrowerId,
                              @PathVariable("libraryId") long libraryId,
                              @PathVariable("bookId") long bookId) {

        Optional<Borrower> borrower = borrowerRepository.findById(borrowerId);
        Optional<LibraryBranch> libraryBranch = libraryBranchRepository.findById(libraryId);
        Optional<Book> book = bookRepository.findById(bookId);


        if (borrower.isPresent() && libraryBranch.isPresent() && book.isPresent()) {

            return borrowerService.checkoutBookFromLibraryByBorrower(
                    borrower.get(), libraryBranch.get(), book.get());
        } else {

            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).build();
        }
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            path = {"/{borrowerId}/libraries/{libraryId}/book:return/{bookId}/return"},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
            )
    public ResponseEntity returnBook(@PathVariable("borrowerId") long borrowerId,
                              @PathVariable("libraryId") long libraryId,
                              @PathVariable("bookId") long bookId) {

        Optional<Borrower> borrower = borrowerRepository.findById(borrowerId);
        Optional<LibraryBranch> libraryBranch = libraryBranchRepository.findById(libraryId);
        Optional<Book> book = bookRepository.findById(bookId);

        if (borrower.isPresent() && libraryBranch.isPresent() && book.isPresent()) {
            Optional<Loans> loans = loansRepository.findAllByBorrowerAndLibraryBranchAndBook(
                    borrower.get(), libraryBranch.get(),book.get() );

            if (loans.isPresent()) {
                loopTerminator.terminate(loans.get());
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
