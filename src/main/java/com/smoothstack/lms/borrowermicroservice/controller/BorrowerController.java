package com.smoothstack.lms.borrowermicroservice.controller;


import com.smoothstack.lms.common.model.*;
import com.smoothstack.lms.common.repository.BookCommonRepository;
import com.smoothstack.lms.common.repository.BorrowerCommonRepository;
import com.smoothstack.lms.common.repository.BranchCommonRepository;
import com.smoothstack.lms.common.repository.LoansCommonRepository;
import com.smoothstack.lms.common.util.LoopTerminator;
import com.smoothstack.lms.common.util.Response;
import com.smoothstack.lms.borrowermicroservice.service.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping({"/borrower","/borrowers"} )
public class BorrowerController {

    //TODO: Change to setter-based injection

    @Autowired
    private BorrowerService borrowerService;

    @Autowired
    private BookCommonRepository bookRepository;

    @Autowired
    private BorrowerCommonRepository borrowerRepository;

    @Autowired
    private LoansCommonRepository loansRepository;

    @Autowired
    private BranchCommonRepository libraryBranchRepository;

    @Autowired
    private LoopTerminator loopTerminator;

    @Autowired
    public void setBorrowerService(BorrowerService borrowerService) {
        this.borrowerService = borrowerService;
    }



    @RequestMapping(
            method = RequestMethod.GET,
            path = {"/libraries/{libraryId}/books:list"},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
            )
    public ResponseEntity listBook(@PathVariable("libraryId") long libraryId,
                                                 @RequestParam("minCopies") Optional<Integer> minCopies) {

        Optional<Branch> libraryBranch = libraryBranchRepository.findById(libraryId);

        if (libraryBranch.isPresent()) {

            Response response = borrowerService
                        .getBookListAtLibrary(libraryBranch.get(), minCopies.orElse(1));
            response.getPayload().getRequest().put("libraryId", libraryId);
            response.getPayload().getRequest().put("minCopies", minCopies.orElse(1));
            return response.buildResponseEntity();
        } else {
            Response response = new Response(HttpStatus.NOT_FOUND);
            response.getPayload().getRequest().put("libraryId", libraryId);
            response.getPayload().getRequest().put("minCopies", minCopies.orElse(1));
            return response.buildResponseEntity();
        }
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = {"/{borrowerId}/books:list"},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
            )
    public ResponseEntity listLoans(@PathVariable("borrowerId") long borrowerId) {

        Optional<Borrower> borrower = borrowerRepository.findById(borrowerId);

        if (borrower.isPresent()) {

            Response response = borrowerService.getBookListBorrowedByBorrower(borrower.get());
            response.getPayload().getRequest().put("borrowerId", borrowerId);
            return response.buildResponseEntity();

        } else {
            Response response = new Response(HttpStatus.NOT_FOUND);
            response.getPayload().getRequest().put("borrowerId", borrowerId);
            return response.buildResponseEntity();
        }
    }

    @RequestMapping(
            method = RequestMethod.POST,
            path = {"/{borrowerId}/libraries/{libraryId}/book:checkout/{bookId}"},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
            )
    public ResponseEntity borrowBook(@PathVariable("borrowerId") long borrowerId,
                              @PathVariable("libraryId") long libraryId,
                              @PathVariable("bookId") long bookId) {

        Optional<Borrower> borrower = borrowerRepository.findById(borrowerId);
        Optional<Branch> libraryBranch = libraryBranchRepository.findById(libraryId);
        Optional<Book> book = bookRepository.findById(bookId);


        if (borrower.isPresent() && libraryBranch.isPresent() && book.isPresent()) {

            Response response =  borrowerService.checkoutBookFromLibraryByBorrower(
                    borrower.get(), libraryBranch.get(), book.get());
            response.getPayload().getRequest().put("borrowerId", borrowerId);
            response.getPayload().getRequest().put("libraryId", libraryId);
            response.getPayload().getRequest().put("bookId", bookId);

            return response.buildResponseEntity();
        } else {
            Response response = new Response(HttpStatus.FAILED_DEPENDENCY);
            response.getPayload().getRequest().put("borrowerId", borrowerId);
            response.getPayload().getRequest().put("libraryId", libraryId);
            response.getPayload().getRequest().put("bookId", bookId);
            return response.buildResponseEntity();
        }
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            path = {"/{borrowerId}/libraries/{libraryId}/book:return/{bookId}"},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
            )
    public ResponseEntity returnBook(@PathVariable("borrowerId") long borrowerId,
                              @PathVariable("libraryId") long libraryId,
                              @PathVariable("bookId") long bookId) {

        Optional<Borrower> borrower = borrowerRepository.findById(borrowerId);
        Optional<Branch> libraryBranch = libraryBranchRepository.findById(libraryId);
        Optional<Book> book = bookRepository.findById(bookId);

        if (borrower.isPresent() && libraryBranch.isPresent() && book.isPresent()) {
            Optional<Loans> loans = loansRepository.findAllByBorrowerAndLibraryBranchAndBook(
                    borrower.get(), libraryBranch.get(),book.get() );

            if (loans.isPresent()) {
                Response response =  borrowerService.returnBookToLibraryByBorrower(
                        loans.get());
                response.getPayload().getRequest().put("borrowerId", borrowerId);
                response.getPayload().getRequest().put("libraryId", libraryId);
                response.getPayload().getRequest().put("bookId", bookId);
                return response.buildResponseEntity();
            } else {
                Response response = new Response(HttpStatus.NOT_FOUND);
                response.getPayload().getRequest().put("borrowerId", borrowerId);
                response.getPayload().getRequest().put("libraryId", libraryId);
                response.getPayload().getRequest().put("bookId", bookId);
                return response.buildResponseEntity();
            }
        } else {

            Response response = new Response(HttpStatus.FAILED_DEPENDENCY);
            response.getPayload().getRequest().put("borrowerId", borrowerId);
            response.getPayload().getRequest().put("libraryId", libraryId);
            response.getPayload().getRequest().put("bookId", bookId);
            return response.buildResponseEntity();
        }
    }
}
