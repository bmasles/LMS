package com.smoothstack.lms.borrowermicroservice.service;

import com.smoothstack.lms.common.model.*;
import com.smoothstack.lms.common.repository.RepositoryAdapter;
import com.smoothstack.lms.common.service.BorrowerCommonService;
import com.smoothstack.lms.common.util.Response;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowerService extends BorrowerCommonService {

    @Transactional
    public Response getBookListAtLibrary(Branch branch, int minimumNoOfCopies) {

        return new Response("copiesList", RepositoryAdapter.getCopiesRepository()
                                                .findAvailableBy(branch));
    }

    @Transactional
    public Response getBookListBorrowedByBorrower(Borrower borrower) {

        return new Response("loansList", RepositoryAdapter.getLoansRepository()
                                                .findAllBy(borrower));
    }

    @Transactional
    public Response checkoutBookFromLibraryByBorrower(Borrower borrower, Branch branch, Book book) {

        if (borrower == null || branch == null || book == null) {
            return new Response(HttpStatus.BAD_REQUEST);
        }

        List<Loans> loansList = RepositoryAdapter.getLoansRepository()
                                    .findAllBy(borrower, book);

        if (loansList.size() > 0) {
            return new Response(HttpStatus.CONFLICT);
        }

        Optional<Copies> copies = RepositoryAdapter.getCopiesRepository()
                                    .findAllBy(book, branch, 1);

        if (!copies.isPresent()) {
            return new Response(HttpStatus.NOT_FOUND);
        }

        copies.get().setCopiesAmount(copies.get().getCopiesAmount() - 1);

        LocalDate dateOut = LocalDate.now();
        LocalDate dueDate = dateOut.plusDays(7);

        Loans loans = new Loans(book, branch, borrower, dateOut, dueDate, null);

        RepositoryAdapter.getCopiesRepository().save(copies.get());
        RepositoryAdapter.getLoansRepository().save(loans);

        Optional<Loans> loansChecked = RepositoryAdapter.getLoansRepository()
                .findAllBy(borrower, branch, book);

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

        Optional<Copies> copies = RepositoryAdapter.getCopiesRepository()
                .findAllBy(loans.getBook(),loans.getBranch());

        if (!copies.isPresent())
            return new Response(HttpStatus.FAILED_DEPENDENCY);

        Optional<Loans> loansChecked = RepositoryAdapter.getLoansRepository()
                .findAllBy(loans.getBorrower(), loans.getBranch(), loans.getBook());

        if (!loansChecked.isPresent())
            return new Response(HttpStatus.FAILED_DEPENDENCY);

        LocalDate dateIn = LocalDate.now();
        loansChecked.get().setLoanDateIn(dateIn);
        copies.get().setCopiesAmount(copies.get().getCopiesAmount() + 1);

        RepositoryAdapter.getLoansRepository()
                .save(loansChecked.get());
        RepositoryAdapter.getCopiesRepository()
                .save(copies.get());

        return new Response(HttpStatus.ACCEPTED, loansChecked.get());
    }

    @Transactional
    public Response buildAndSave(Borrower borrower) {

        super.save(borrower);

        return new Response(HttpStatus.CREATED, borrower);
    }
}
