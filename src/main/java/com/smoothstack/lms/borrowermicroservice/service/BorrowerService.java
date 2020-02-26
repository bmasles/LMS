package com.smoothstack.lms.borrowermicroservice.service;

import com.smoothstack.lms.common.model.*;
import com.smoothstack.lms.common.repository.BookCommonRepository;
import com.smoothstack.lms.common.repository.BorrowerCommonRepository;
import com.smoothstack.lms.common.repository.CopiesCommonRepository;
import com.smoothstack.lms.common.repository.LoansCommonRepository;
import com.smoothstack.lms.common.util.LoopTerminator;
import com.smoothstack.lms.common.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowerService {

    private BookCommonRepository bookRepository;

    private BorrowerCommonRepository borrowerRepository;

    private CopiesCommonRepository copiesRepository;

    private LoansCommonRepository loansRepository;

    private LoopTerminator loopTerminator;

    @Autowired
    public void setBookRepository(BookCommonRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Autowired
    public void setBorrowerRepository(BorrowerCommonRepository borrowerRepository) {
        this.borrowerRepository = borrowerRepository;
    }

    @Autowired
    public void setCopiesRepository(CopiesCommonRepository copiesRepository) {
        this.copiesRepository = copiesRepository;
    }

    @Autowired
    public void setLoansRepository(LoansCommonRepository loansRepository) {
        this.loansRepository = loansRepository;
    }

    @Autowired
    public void setLoopTerminator(LoopTerminator loopTerminator) {
        this.loopTerminator = loopTerminator;
    }

    @Transactional
    public Response getBookListAtLibrary(Branch branch, int minimumNoOfCopies) {

        return new Response("copiesList",
                copiesRepository.findAllByLibraryBranchAndNoOfCopiesGreaterThanEqual(branch, minimumNoOfCopies));
    }

    @Transactional
    public Response getBookListBorrowedByBorrower(Borrower borrower) {

        return new Response("loansList", loansRepository.findAllByBorrower(borrower));
    }

    @Transactional
    public Response checkoutBookFromLibraryByBorrower(Borrower borrower, Branch branch, Book book) {


        if (borrower == null || branch == null || book == null) {
            return new Response(HttpStatus.BAD_REQUEST);
        }


        List<Loans> loansList = loansRepository.findAllByBorrowerAndBook(borrower, book);

        if (loansList.size() > 0) {
            return new Response(HttpStatus.CONFLICT);
        }


        Optional<Copies> copies = copiesRepository
                .findAllByBookAndLibraryBranchAndNoOfCopiesGreaterThanEqual(book, branch, 1);

        if (!copies.isPresent()) {
            return new Response(HttpStatus.NOT_FOUND);
        }

        copies.get().setCopiesAmount(copies.get().getCopiesAmount() - 1);


        LocalDate dateOut = LocalDate.now();
        LocalDate dueDate = dateOut.plusDays(7);

        Loans loans = new Loans(book, branch, borrower, dateOut, dueDate, null);

        copiesRepository.save(copies.get());
        loansRepository.save(loans);


        Optional<Loans> loansChecked = loansRepository.findAllByBorrowerAndLibraryBranchAndBook(borrower, branch, book);

        if (loansChecked.isPresent() && loansChecked.get().getLoanDueDate() == null) {
            return new Response(HttpStatus.CREATED, loansChecked.get());
        } else {
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Transactional
    public Response returnBookToLibraryByBorrower(Loans loans) {


        if (loans == null)
            return new Response(HttpStatus.BAD_REQUEST);


        Optional<Copies> copies = copiesRepository
                .findAllByBookAndLibraryBranchAndNoOfCopiesGreaterThanEqual(loans.getBook(),
                        loans.getBranch(), 0);


        if (!copies.isPresent())
            return new Response(HttpStatus.FAILED_DEPENDENCY);


        Optional<Loans> loansChecked = loansRepository.findAllByBorrowerAndLibraryBranchAndBook(
                loans.getBorrower(), loans.getBranch(), loans.getBook()
        );

        if (!loansChecked.isPresent())
            return new Response(HttpStatus.FAILED_DEPENDENCY);


        LocalDate dateIn = LocalDate.now();
        loansChecked.get().setLoanDateIn(dateIn);
        copies.get().setCopiesAmount(copies.get().getCopiesAmount() + 1);

        loansRepository.save(loansChecked.get());
        copiesRepository.save(copies.get());

        return new Response(HttpStatus.ACCEPTED, loansChecked.get());
    }

    @Transactional
    public Response buildAndSave(Borrower borrower) {

        borrowerRepository.save(borrower);
        borrowerRepository.flush();
        return new Response(HttpStatus.CREATED, borrower);
    }
}
