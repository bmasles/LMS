package com.smoothstack.lms.common.repository;

import com.smoothstack.lms.common.model.Book;
import com.smoothstack.lms.common.model.Borrower;
import com.smoothstack.lms.common.model.Branch;
import com.smoothstack.lms.common.model.Loans;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoansCommonRepository extends JpaRepository<Loans, Long> {

    List<Loans> findAllByBorrower(Borrower borrower);

    List<Loans> findAllByBorrowerAndBook(Borrower borrower, Book book);

    Optional<Loans> findAllByBorrowerAndLibraryBranchAndBook(Borrower borrower, Branch branch, Book book);
}
