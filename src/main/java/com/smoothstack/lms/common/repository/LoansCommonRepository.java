package com.smoothstack.lms.common.repository;

import com.smoothstack.lms.common.model.Book;
import com.smoothstack.lms.common.model.Borrower;
import com.smoothstack.lms.common.model.Branch;
import com.smoothstack.lms.common.model.Loans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoansCommonRepository extends JpaRepository<Loans, Long> {

    boolean existsByBook(Book book);

    boolean existsByBorrower(Borrower borrower);

    boolean existsByBranch(Branch branch);

    default List<Loans> findAllBy(Borrower borrower, Book book) {
        return findAllByBorrowerAndBook(borrower, book);
    }
    List<Loans> findAllByBorrowerAndBook(Borrower borrower, Book book);

    default List<Loans> findAllBy(Borrower borrower) {
        return findAllByBorrower(borrower);
    }
    List<Loans> findAllByBorrower(Borrower borrower);

    default Optional<Loans> findAllBy(Borrower borrower, Branch branch, Book book) {
        return findAllByBorrowerAndBranchAndBook(borrower, branch, book);
    }
    Optional<Loans> findAllByBorrowerAndBranchAndBook(Borrower borrower, Branch branch, Book book);

}
