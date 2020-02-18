package com.smoothstack.lms.borrowermicroservice.repository.extractor;

import com.smoothstack.lms.borrowermicroservice.Debug;
import com.smoothstack.lms.borrowermicroservice.model.Author;
import com.smoothstack.lms.borrowermicroservice.model.Book;
import com.smoothstack.lms.borrowermicroservice.model.Borrower;
import com.smoothstack.lms.borrowermicroservice.model.Copies;
import com.smoothstack.lms.borrowermicroservice.model.Genre;
import com.smoothstack.lms.borrowermicroservice.model.Library;
import com.smoothstack.lms.borrowermicroservice.model.Loans;
import com.smoothstack.lms.borrowermicroservice.model.Publisher;

import java.sql.ResultSet;
import java.sql.SQLException;


public abstract class DataExtractor {

    private static int[] getCustomIndex(int start, int size, int... customIndex) {
        int[] idx = new int[size];

        for (int i = start, c = 0; c < size; c++, i++) {
            if (c < customIndex.length)
                idx[c] = customIndex[c];
            else
                idx[c] = i;
        }

        return idx;
    }

    public static Book getBook(ResultSet resultSet, int... customIndex) {

        int[] index = getCustomIndex(1, 7, customIndex);

        Book book = new Book();

        try {
            book.setId(resultSet.getInt(index[0]));
            book.setTitle(resultSet.getString(index[1]));

            Publisher publisher = new Publisher();
            publisher.setId(resultSet.getInt(index[2]));
            book.setPublisher(publisher);

            publisher = getPublisher(resultSet, index[3], index[4], index[5], index[6]);
            book.setPublisher(publisher);

        } catch (SQLException e) {
            Debug.printException(e);
        }

        return book;
    }

    public static Publisher getPublisher(ResultSet resultSet, int... customIndex) {

        int[] index = getCustomIndex(1, 4, customIndex);

        Publisher publisher = new Publisher();

        try {
            publisher.setId(resultSet.getInt(index[0]));
            publisher.setName(resultSet.getString(index[1]));

            publisher.setAddress(resultSet.getString(index[2]));
            publisher.setPhone(resultSet.getString(index[3]));

        } catch (SQLException e) {
            Debug.printException(e);
        }

        return publisher;
    }

    public static Author getAuthor(ResultSet resultSet, int... customIndex) {

        int[] index = getCustomIndex(1, 2, customIndex);

        Author author = new Author();
        try {
            author.setId(resultSet.getInt(index[0]));
            author.setName(resultSet.getString(index[1]));
        } catch (SQLException e) {
            Debug.printException(e);
        }
        return author;
    }

    public static Library getLibrary(ResultSet resultSet, int... customIndex) {

        int[] index = getCustomIndex(1, 3, customIndex);

        Library library = new Library();
        try {
            library.setId(resultSet.getInt(index[0]));
            library.setName(resultSet.getString(index[1]));
            library.setAddress(resultSet.getString(index[2]));
        } catch (SQLException e) {
            Debug.printException(e);
        }
        return library;
    }

    public static Borrower getBorrower(ResultSet resultSet, int... customIndex) {

        int[] index = getCustomIndex(1, 4, customIndex);

        Borrower borrower = new Borrower();
        try {
            borrower.setId(resultSet.getInt(index[0]));
            borrower.setName(resultSet.getString(index[1]));
            borrower.setAddress(resultSet.getString(index[2]));
            borrower.setPhone(resultSet.getString(index[3]));
        } catch (SQLException e) {
            Debug.printException(e);
        }
        return borrower;
    }

    public static Loans getLoans(ResultSet resultSet, int... customIndex) {

        int[] index = getCustomIndex(1, 17, customIndex);

        Loans loans = new Loans();

        try {
            Book book = getBook(resultSet, index[0], index[1], index[2], index[3], index[4], index[5], index[6]);
            loans.setBook(book);

            Library library = getLibrary(resultSet, index[7], index[8], index[9]);
            loans.setLibrary(library);

            Borrower borrower = getBorrower(resultSet, index[10], index[11], index[12], index[13]);
            loans.setBorrower(borrower);

            loans.setOut(resultSet.getDate(index[14]));

            loans.setDue(resultSet.getDate(index[15]));

            loans.setIn(resultSet.getDate(index[16]));


        } catch (SQLException e) {
            Debug.printException(e);
            return null;
        }
        return loans;
    }

    public static Copies getCopies(ResultSet resultSet, int... customIndex) {

        int[] index = getCustomIndex(1, 11, customIndex);

        Copies copies = new Copies();

        try {
            Book book = getBook(resultSet, index[0], index[1], index[2], index[3], index[4], index[5], index[6]);
            copies.setBook(book);

            Library library = getLibrary(resultSet, index[7], index[8], index[9]);
            copies.setLibrary(library);

            copies.setNoOfCopies(resultSet.getInt(index[10]));

        } catch (SQLException e) {
            Debug.printException(e);
        }
        return copies;
    }

    public static Genre getGenre(ResultSet resultSet, int... customIndex) {

        int[] index = getCustomIndex(1, 2, customIndex);

        Genre genre = new Genre();

        try {
            genre.setId(resultSet.getInt(index[0]));
            genre.setName(resultSet.getString(index[1]));

        } catch (SQLException e) {
            Debug.printException(e);
        }
        return genre;
    }
}
