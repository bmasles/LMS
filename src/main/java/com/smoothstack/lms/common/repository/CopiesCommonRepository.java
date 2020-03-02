package com.smoothstack.lms.common.repository;

import com.smoothstack.lms.common.model.Book;
import com.smoothstack.lms.common.model.Branch;
import com.smoothstack.lms.common.model.Copies;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CopiesCommonRepository extends JpaRepository<Copies, Long> {


    default Optional<Copies> findById(Long bookId, Long branchId) {
        return findByBook_BookIdAndBranch_BranchId(bookId, branchId);
    }
    Optional<Copies> findByBook_BookIdAndBranch_BranchId(Long bookId, Long branchId);


    default boolean existsBy(Book book) {
        return existsByBook(book);
    }
    boolean existsByBook(Book book);
    default boolean existsBy(Branch branch) {
        return existsByBranch(branch);
    }
    boolean existsByBranch(Branch branch);


    void deleteAllByBook(Book book);


    default List<Copies> findAllBy(Branch branch, int minimumNoOfCopies) {
        return findAllByBranchAndCopiesAmountGreaterThanEqual(branch,minimumNoOfCopies);
    }
    default List<Copies> findAllBy(Branch branch) {
        return findAllBy(branch,0);
    }
    default List<Copies> findAvailableBy(Branch branch) {
        return findAllBy(branch,1);
    }
    List<Copies> findAllByBranchAndCopiesAmountGreaterThanEqual(Branch branch, int minimumNoOfCopies);

    default Optional<Copies> findAllBy(Book book, Branch branch, int minimumNoOfCopies) {
        return findAllByBookAndBranchAndCopiesAmountGreaterThanEqual(book, branch,minimumNoOfCopies);
    }
    default Optional<Copies> findAllBy(Book book, Branch branch) {
        return findAllBy(book, branch,0);
    }
    default Optional<Copies> findAvailableBy(Book book, Branch branch) {
        return findAllBy(book, branch,1);
    }
    Optional<Copies> findAllByBookAndBranchAndCopiesAmountGreaterThanEqual(Book book, Branch branch, int minimumNoOfCopies);
}
