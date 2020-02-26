package com.smoothstack.lms.common.repository;

import com.smoothstack.lms.common.model.Book;
import com.smoothstack.lms.common.model.Copies;
import com.smoothstack.lms.common.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CopiesCommonRepository extends JpaRepository<Copies, Long> {

    List<Copies> findAllByLibraryBranchAndNoOfCopiesGreaterThanEqual(Branch branch, int minimumNoOfCopies);

    Optional<Copies> findAllByBookAndLibraryBranchAndNoOfCopiesGreaterThanEqual(Book book, Branch branch, int minimumNoOfCopies);
}
