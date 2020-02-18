package com.smoothstack.lms.borrowermicroservice.repository.extractor;

import com.smoothstack.lms.borrowermicroservice.model.Book;
import com.smoothstack.lms.borrowermicroservice.model.Publisher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.ResultSet;
import java.sql.SQLException;

//TODO: TestDataExtractor

@RunWith(MockitoJUnitRunner.class)
class DataExtractorTest {


    ResultSet mockBookRs() throws SQLException {

        ResultSet resultSet = Mockito.mock(ResultSet.class);;
        int i = 0;
        Mockito.when(resultSet.getInt(++i)).thenReturn(5);
        Mockito.when(resultSet.getString(++i)).thenReturn("Book Title");
        Mockito.when(resultSet.getInt(++i)).thenReturn(10);

        Mockito.when(resultSet.getInt(++i)).thenReturn(10);
        Mockito.when(resultSet.getString(++i)).thenReturn("Publisher Name");
        Mockito.when(resultSet.getString(++i)).thenReturn("Publisher Address");
        Mockito.when(resultSet.getString(++i)).thenReturn("555-987-6543");
        return resultSet;
    }

    ResultSet mockPublisherRs() throws SQLException {

        ResultSet resultSet = Mockito.mock(ResultSet.class);;
        int i = 0;
        Mockito.when(resultSet.getInt(++i)).thenReturn(10);
        Mockito.when(resultSet.getString(++i)).thenReturn("Publisher Name");
        Mockito.when(resultSet.getString(++i)).thenReturn("Publisher Address");
        Mockito.when(resultSet.getString(++i)).thenReturn("555-987-6543");
        return resultSet;
    }




    @Test
    void getBook() throws SQLException {
        Book book = DataExtractor.getBook(mockBookRs());

        Assertions.assertEquals(5, book.getId(), "Book::id");
        Assertions.assertEquals("Book Title", book.getTitle(), "Book::title");
        Assertions.assertEquals(10, book.getPublisher().getId(), "Publisher::id");

    }

    @Test
    void getPublisher() throws SQLException {
        Publisher publisher = DataExtractor.getPublisher(mockPublisherRs());

        Assertions.assertEquals(10, publisher.getId(), "Publisher::id");
        Assertions.assertEquals("Publisher Name", publisher.getName(), "Publisher::name");
        Assertions.assertEquals("Publisher Address", publisher.getAddress(), "Publisher::address");
        Assertions.assertEquals("555-987-6543", publisher.getPhone(), "Publisher::phone");
    }

    @Test
    void getAuthor() {
    }

    @Test
    void getLibrary() {
    }

    @Test
    void getBorrower() {
    }



    @Test
    void getLoans() {
    }

    @Test
    void getCopies() {
    }


}