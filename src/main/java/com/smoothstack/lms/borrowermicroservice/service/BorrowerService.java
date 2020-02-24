package com.smoothstack.lms.borrowermicroservice.service;

import com.smoothstack.lms.borrowermicroservice.common.model.*;
import com.smoothstack.lms.borrowermicroservice.common.repository.BookRepository;
import com.smoothstack.lms.borrowermicroservice.common.repository.BorrowerRepository;
import com.smoothstack.lms.borrowermicroservice.common.repository.CopiesRepository;
import com.smoothstack.lms.borrowermicroservice.common.repository.LoansRepository;
import com.smoothstack.lms.borrowermicroservice.common.util.LoopTerminator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowerService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowerRepository borrowerRepository;

    @Autowired
    private CopiesRepository copiesRepository;

    @Autowired
    private LoansRepository loansRepository;

    @Autowired
    private LoopTerminator loopTerminator;

    public List<Copies> getBookListAtLibrary(LibraryBranch libraryBranch, int minimumNoOfCopies) {

//        try (Connection connection = connectionFactory.getConnection() ) {
//            return DataAccess.executeQuery(connection,
//                    "SELECT b.bookId, b.title, b.pubId, " +
//                            "p.publisherId, p.publisherName, p.publisherAddress, p.publisherPhone, " +
//                            "l.branchId, l.branchName, l.branchAddress, " +
//                            "c.noOfCopies " +
//
//                            "FROM tbl_book b " +
//                            "JOIN tbl_publisher p on b.pubId = p.publisherId " +
//                            "JOIN tbl_book_copies c on b.bookId = c.bookId " +
//                            "JOIN tbl_library_branch l on l.branchId = c.branchId " +
//
//                            "WHERE c.branchId = ? AND c.noOfCopies >= ? "
//                    , DataExtractor::getCopies
//                    , library.getId(), minimumNoOfCopies);
//
//        } catch (SQLException e) {
//            Debug.printException(e);
//            return Collections.emptyList();
//        }

        return copiesRepository.findAllByLibraryBranchAndNoOfCopiesGreaterThanEqual(libraryBranch, minimumNoOfCopies);
    }

    public List<Loans> getBookListBorrowedByBorrower(Borrower borrower) {
//        try (Connection connection = connectionFactory.getConnection() ) {
//
//            return DataAccess.executeQuery(connection,
//                    "SELECT b.bookId, b.title, b.pubId, " +
//                            "p.publisherId, p.publisherName, p.publisherAddress, p.publisherPhone, " +
//                            "l.branchId, l.branchName, l.branchAddress, " +
//                            "r.cardNo, r.name, r.address, r.phone," +
//                            "o.dateOut, o.dueDate, o.dateIn " +
//                            "FROM tbl_book_loans o " +
//                            "JOIN tbl_book b on b.bookId = o.bookId " +
//                            "JOIN tbl_publisher p on p.publisherId = b.pubId " +
//                            "JOIN tbl_library_branch l on l.branchId = o.branchId " +
//                            "JOIN tbl_borrower r on r.cardNo = o.cardNo " +
//                            "WHERE o.dateIn is null AND o.cardNo = ? "
//                    , DataExtractor::getLoans
//                    , borrower.getId());
//
//        } catch (SQLException e) {
//            Debug.printException(e);
//            return Collections.emptyList();
//        }

        return loansRepository.findAllByBorrower(borrower);
    }

    @Transactional
    public ResponseEntity checkoutBookFromLibraryByBorrower(Borrower borrower, LibraryBranch libraryBranch, Book book ) {
//        try (Connection connection = connectionFactory.getConnection() ) {

        if (borrower == null || libraryBranch == null || book == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

//            List<Loans> loansList = DataAccess.executeQuery(connection,
//                    "SELECT b.bookId, b.title, b.pubId, " +
//                            "p.publisherId, p.publisherName, p.publisherAddress, p.publisherPhone, " +
//                            "l.branchId, l.branchName, l.branchAddress, " +
//                            "r.cardNo, r.name, r.address, r.phone," +
//                            "o.dateOut, o.dueDate, o.dateIn " +
//                            "FROM tbl_book_loans o " +
//                            "JOIN tbl_book b on b.bookId = o.bookId " +
//                            "JOIN tbl_publisher p on p.publisherId = b.pubId " +
//                            "JOIN tbl_library_branch l on l.branchId = o.branchId " +
//                            "JOIN tbl_borrower r on r.cardNo = o.cardNo " +
//                            "WHERE o.dateIn is null AND o.cardNo = ? AND b.bookId = ?"
//                    , DataExtractor::getLoans
//                    , borrower.getId()
//                    , book.getId()
//            );

        List<Loans> loansList = loansRepository.findAllByBorrowerAndBook(borrower, book);

        if (loansList.size() > 0)
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

//            List<Copies> copies = DataAccess.executeQuery(connection,
//                    "SELECT b.bookId, b.title, b.pubId, " +
//                            "p.publisherId, p.publisherName, p.publisherAddress, p.publisherPhone, " +
//                            "l.branchId, l.branchName, l.branchAddress, " +
//                            "c.noOfCopies " +
//
//                            "FROM tbl_book b " +
//                            "JOIN tbl_publisher p on b.pubId = p.publisherId " +
//                            "JOIN tbl_book_copies c on b.bookId = c.bookId " +
//                            "JOIN tbl_library_branch l on l.branchId = c.branchId " +
//
//                            "WHERE c.branchId = ? AND b.bookId = ? AND c.noOfCopies >= ? "
//                    , DataExtractor::getCopies
//                    , libraryBranch.getId(), book.getId(), 1);
//TODO:
        Optional<Copies> copies = copiesRepository
                    .findAllByBookAndLibraryBranchAndNoOfCopiesGreaterThanEqual(book, libraryBranch,1);

        if (!copies.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        copies.get().setNoOfCopies(copies.get().getNoOfCopies()-1);

//            DataAccess.executeUpdate(connection,
//                    "UPDATE tbl_book_copies SET noOfCopies = noOfCopies - 1 " +
//                            "WHERE noOfCopies > 0 AND bookId = ? AND branchId = ? "
//                    , Statement.NO_GENERATED_KEYS, book.getId() , libraryBranch.getId()
//            );



        LocalDate dateOut = LocalDate.now();
        LocalDate dueDate = dateOut.plusDays(7);

        Loans loans = new Loans(book, libraryBranch, borrower, dateOut, dueDate, null);

        copiesRepository.save(copies.get());
        loansRepository.save(loans);


//            DataAccess.executeUpdate(connection,
//                    "INSERT INTO tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate) " +
//                            "VALUES  (?,?,?,?,?)"
//                    ,Statement.NO_GENERATED_KEYS, book.getId() ,libraryBranch.getId(), borrower.getId(), jDateOut, jDueDate);
//
//            loansList = DataAccess.executeQuery(connection,
//                    "SELECT b.bookId, b.title, b.pubId, " +
//                            "p.publisherId, p.publisherName, p.publisherAddress, p.publisherPhone, " +
//                            "l.branchId, l.branchName, l.branchAddress, " +
//                            "r.cardNo, r.name, r.address, r.phone," +
//                            "o.dateOut, o.dueDate, o.dateIn " +
//                            "FROM tbl_book_loans o " +
//                            "JOIN tbl_book b on b.bookId = o.bookId " +
//                            "JOIN tbl_publisher p on p.publisherId = b.pubId " +
//                            "JOIN tbl_library_branch l on l.branchId = o.branchId " +
//                            "JOIN tbl_borrower r on r.cardNo = o.cardNo " +
//                            "WHERE o.dateIn IS null AND o.cardNo = ? AND l.branchId = ? AND b.bookId = ?"
//                    , DataExtractor::getLoans
//                    , borrower.getId()
//                    , libraryBranch.getId()
//                    , book.getId()
//            );
//
//            connection.commit();

            // Double check that the loan record was insert into database

        Optional<Loans> loansChecked = loansRepository.findAllByBorrowerAndLibraryBranchAndBook(borrower, libraryBranch, book);

        if (loansChecked.isPresent() && loansChecked.get().getDue() == null)
            return ResponseEntity.status(HttpStatus.CREATED).body(loansList);
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(loansChecked.get());

//        } catch (SQLException e) {
//            Debug.printException(e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }

    }

    public ResponseEntity returnBookToLibraryByBorrower(Loans loans) {
//        try (Connection connection = connectionFactory.getConnection()) {

        if (loans == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

//            DataAccess.executeUpdate(connection,
//                    "UPDATE tbl_book_copies SET noOfCopies = noOfCopies + 1 " +
//                            "WHERE bookId = ? AND branchId = ? "
//                    , Statement.NO_GENERATED_KEYS
//                    , loans.getBook().getId(), loans.getLibrary().getId()
//            );

        Optional<Copies> copies = copiesRepository
                .findAllByBookAndLibraryBranchAndNoOfCopiesGreaterThanEqual(loans.getBook(), loans.getLibraryBranch(), 0);



        if (!copies.isPresent())
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).build();


        Optional<Loans> loansChecked = loansRepository.findAllByBorrowerAndLibraryBranchAndBook(
                loans.getBorrower(), loans.getLibraryBranch(), loans.getBook()
        );

        if (!loansChecked.isPresent())
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).build();

//            DataAccess.executeUpdate(connection,
//                    "UPDATE tbl_book_loans SET dateIn = ? " +
//                            "WHERE bookId = ? AND branchId = ? AND cardNo = ? AND dateIn IS null"
//                    , Statement.NO_GENERATED_KEYS
//                    , jDateIn, loans.getBook().getId(), loans.getLibrary().getId(), loans.getBorrower().getId()
//            );

        LocalDate dateIn = LocalDate.now();
        loansChecked.get().setIn(dateIn);
        copies.get().setNoOfCopies(copies.get().getNoOfCopies()+1);

        loansRepository.save(loansChecked.get());
        copiesRepository.save(copies.get());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(loansChecked.get());

//            List<Loans> loansList = DataAccess.executeQuery(connection,
//                    "SELECT b.bookId, b.title, b.pubId, " +
//                            "p.publisherId, p.publisherName, p.publisherAddress, p.publisherPhone, " +
//                            "l.branchId, l.branchName, l.branchAddress, " +
//                            "r.cardNo, r.name, r.address, r.phone," +
//                            "o.dateOut, o.dueDate, o.dateIn " +
//                            "FROM tbl_book_loans o " +
//                            "JOIN tbl_book b on b.bookId = o.bookId " +
//                            "JOIN tbl_publisher p on p.publisherId = b.pubId " +
//                            "JOIN tbl_library_branch l on l.branchId = o.branchId " +
//                            "JOIN tbl_borrower r on r.cardNo = o.cardNo " +
//                            "WHERE o.dateIn IS NOT null AND o.cardNo = ? AND l.branchId = ? AND b.bookId = ?"
//                    , DataExtractor::getLoans
//                    , loans.getBorrower().getId()
//                    , loans.getLibrary().getId()
//                    , loans.getBook().getId()
//            );
//
//            connection.commit();

            // Double check that the loan record was insert into database
//            if (loansList.size() > 0)
//                return ResponseEntity.status(HttpStatus.ACCEPTED).body(loansList);
//            else
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(loansList);
//
//        } catch (SQLException e) {
//            Debug.printException(e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
    }

    @Transactional
    public Borrower buildAndSave(Borrower borrower) {

        borrowerRepository.save(borrower);
        borrowerRepository.flush();
        return borrower;
    }
}
