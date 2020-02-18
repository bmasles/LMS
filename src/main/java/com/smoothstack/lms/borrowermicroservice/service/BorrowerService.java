package com.smoothstack.lms.borrowermicroservice.service;

import com.smoothstack.lms.borrowermicroservice.Debug;
import com.smoothstack.lms.borrowermicroservice.database.ConnectionFactory;
import com.smoothstack.lms.borrowermicroservice.database.DataAccess;
import com.smoothstack.lms.borrowermicroservice.model.*;
import com.smoothstack.lms.borrowermicroservice.repository.extractor.DataExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class BorrowerService {

    private ConnectionFactory connectionFactory;

    @Autowired
    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public List<Copies> getBookListAtLibrary(Library library, int minimumNoOfCopies) {

        try (Connection connection = connectionFactory.getConnection() ) {
            return DataAccess.executeQuery(connection,
                    "SELECT b.bookId, b.title, b.pubId, " +
                            "p.publisherId, p.publisherName, p.publisherAddress, p.publisherPhone, " +
                            "l.branchId, l.branchName, l.branchAddress, " +
                            "c.noOfCopies " +

                            "FROM tbl_book b " +
                            "JOIN tbl_publisher p on b.pubId = p.publisherId " +
                            "JOIN tbl_book_copies c on b.bookId = c.bookId " +
                            "JOIN tbl_library_branch l on l.branchId = c.branchId " +

                            "WHERE c.branchId = ? AND c.noOfCopies >= ? "
                    , DataExtractor::getCopies
                    , library.getId(), minimumNoOfCopies);

        } catch (SQLException e) {
            Debug.printException(e);
            return Collections.emptyList();
        }
    }

    public List<Loans> getBookListBorrowedByBorrower(Borrower borrower) {
        try (Connection connection = connectionFactory.getConnection() ) {

            return DataAccess.executeQuery(connection,
                    "SELECT b.bookId, b.title, b.pubId, " +
                            "p.publisherId, p.publisherName, p.publisherAddress, p.publisherPhone, " +
                            "l.branchId, l.branchName, l.branchAddress, " +
                            "r.cardNo, r.name, r.address, r.phone," +
                            "o.dateOut, o.dueDate, o.dateIn " +
                            "FROM tbl_book_loans o " +
                            "JOIN tbl_book b on b.bookId = o.bookId " +
                            "JOIN tbl_publisher p on p.publisherId = b.pubId " +
                            "JOIN tbl_library_branch l on l.branchId = o.branchId " +
                            "JOIN tbl_borrower r on r.cardNo = o.cardNo " +
                            "WHERE o.dateIn is null AND o.cardNo = ? "
                    , DataExtractor::getLoans
                    , borrower.getId());

        } catch (SQLException e) {
            Debug.printException(e);
            return Collections.emptyList();
        }
    }

    public ResponseEntity checkoutBookFromLibraryByBorrower(Borrower borrower, Library library, Book book ) {
        try (Connection connection = connectionFactory.getConnection() ) {

            if (borrower == null || library == null || book == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

            List<Loans> loansList = DataAccess.executeQuery(connection,
                    "SELECT b.bookId, b.title, b.pubId, " +
                            "p.publisherId, p.publisherName, p.publisherAddress, p.publisherPhone, " +
                            "l.branchId, l.branchName, l.branchAddress, " +
                            "r.cardNo, r.name, r.address, r.phone," +
                            "o.dateOut, o.dueDate, o.dateIn " +
                            "FROM tbl_book_loans o " +
                            "JOIN tbl_book b on b.bookId = o.bookId " +
                            "JOIN tbl_publisher p on p.publisherId = b.pubId " +
                            "JOIN tbl_library_branch l on l.branchId = o.branchId " +
                            "JOIN tbl_borrower r on r.cardNo = o.cardNo " +
                            "WHERE o.dateIn is null AND o.cardNo = ? AND b.bookId = ?"
                    , DataExtractor::getLoans
                    , borrower.getId()
                    , book.getId()
            );
            if (loansList.size() > 0)
                return ResponseEntity.status(HttpStatus.CONFLICT).body(loansList);

            List<Copies> copies = DataAccess.executeQuery(connection,
                    "SELECT b.bookId, b.title, b.pubId, " +
                            "p.publisherId, p.publisherName, p.publisherAddress, p.publisherPhone, " +
                            "l.branchId, l.branchName, l.branchAddress, " +
                            "c.noOfCopies " +

                            "FROM tbl_book b " +
                            "JOIN tbl_publisher p on b.pubId = p.publisherId " +
                            "JOIN tbl_book_copies c on b.bookId = c.bookId " +
                            "JOIN tbl_library_branch l on l.branchId = c.branchId " +

                            "WHERE c.branchId = ? AND b.bookId = ? AND c.noOfCopies >= ? "
                    , DataExtractor::getCopies
                    , library.getId(), book.getId(), 1);

            if (copies.size() == 0)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(copies);

            DataAccess.executeUpdate(connection,
                    "UPDATE tbl_book_copies SET noOfCopies = noOfCopies - 1 " +
                            "WHERE noOfCopies > 0 AND bookId = ? AND branchId = ? "
                    , Statement.NO_GENERATED_KEYS, book.getId() , library.getId()
            );

            LocalDate dateOut = LocalDate.now();
            LocalDate dueDate = dateOut.plusDays(7);

            Date jDateOut = Date.valueOf(dateOut);
            Date jDueDate = Date.valueOf(dueDate);

            DataAccess.executeUpdate(connection,
                    "INSERT INTO tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate) " +
                            "VALUES  (?,?,?,?,?)"
                    ,Statement.NO_GENERATED_KEYS, book.getId() ,library.getId(), borrower.getId(), jDateOut, jDueDate);

            loansList = DataAccess.executeQuery(connection,
                    "SELECT b.bookId, b.title, b.pubId, " +
                            "p.publisherId, p.publisherName, p.publisherAddress, p.publisherPhone, " +
                            "l.branchId, l.branchName, l.branchAddress, " +
                            "r.cardNo, r.name, r.address, r.phone," +
                            "o.dateOut, o.dueDate, o.dateIn " +
                            "FROM tbl_book_loans o " +
                            "JOIN tbl_book b on b.bookId = o.bookId " +
                            "JOIN tbl_publisher p on p.publisherId = b.pubId " +
                            "JOIN tbl_library_branch l on l.branchId = o.branchId " +
                            "JOIN tbl_borrower r on r.cardNo = o.cardNo " +
                            "WHERE o.dateIn IS null AND o.cardNo = ? AND l.branchId = ? AND b.bookId = ?"
                    , DataExtractor::getLoans
                    , borrower.getId()
                    , library.getId()
                    , book.getId()
            );

            connection.commit();

            // Double check that the loan record was insert into database
            if (loansList.size() > 0)
                return ResponseEntity.status(HttpStatus.CREATED).body(loansList);
            else
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(loansList);

        } catch (SQLException e) {
            Debug.printException(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    public ResponseEntity returnBookToLibraryByBorrower(Loans loans) {
        try (Connection connection = connectionFactory.getConnection()) {

            if (loans == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

            DataAccess.executeUpdate(connection,
                    "UPDATE tbl_book_copies SET noOfCopies = noOfCopies + 1 " +
                            "WHERE bookId = ? AND branchId = ? "
                    , Statement.NO_GENERATED_KEYS
                    , loans.getBook().getId(), loans.getLibrary().getId()
            );

            LocalDate dateIn = LocalDate.now();
            Date jDateIn = Date.valueOf(dateIn);

            DataAccess.executeUpdate(connection,
                    "UPDATE tbl_book_loans SET dateIn = ? " +
                            "WHERE bookId = ? AND branchId = ? AND cardNo = ? AND dateIn IS null"
                    , Statement.NO_GENERATED_KEYS
                    , jDateIn, loans.getBook().getId(), loans.getLibrary().getId(), loans.getBorrower().getId()
            );

            List<Loans> loansList = DataAccess.executeQuery(connection,
                    "SELECT b.bookId, b.title, b.pubId, " +
                            "p.publisherId, p.publisherName, p.publisherAddress, p.publisherPhone, " +
                            "l.branchId, l.branchName, l.branchAddress, " +
                            "r.cardNo, r.name, r.address, r.phone," +
                            "o.dateOut, o.dueDate, o.dateIn " +
                            "FROM tbl_book_loans o " +
                            "JOIN tbl_book b on b.bookId = o.bookId " +
                            "JOIN tbl_publisher p on p.publisherId = b.pubId " +
                            "JOIN tbl_library_branch l on l.branchId = o.branchId " +
                            "JOIN tbl_borrower r on r.cardNo = o.cardNo " +
                            "WHERE o.dateIn IS NOT null AND o.cardNo = ? AND l.branchId = ? AND b.bookId = ?"
                    , DataExtractor::getLoans
                    , loans.getBorrower().getId()
                    , loans.getLibrary().getId()
                    , loans.getBook().getId()
            );

            connection.commit();

            // Double check that the loan record was insert into database
            if (loansList.size() > 0)
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(loansList);
            else
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(loansList);

        } catch (SQLException e) {
            Debug.printException(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
