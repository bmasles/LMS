package com.smoothstack.lms.adminmicroservice.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.smoothstack.lms.adminmicroservice.model.Author;
import com.smoothstack.lms.adminmicroservice.model.Book;
import com.smoothstack.lms.adminmicroservice.model.Genre;
import com.smoothstack.lms.adminmicroservice.model.Publisher;

@Component
public class BookDAO extends BaseDAO<Book> {

	public BookDAO() throws ClassNotFoundException, SQLException {
		super();
	}
	
	public Integer addBook(Book book) throws ClassNotFoundException, SQLException {
		return save("insert into tbl_book (title,pubId) values (?,?)", 
				new Object[] { book.getTitle(),book.getPublisher().getPublisherId() });
	}

	public void updateBook(Book book) throws SQLException, ClassNotFoundException {
		save("update tbl_book set title = ?, pubId = ? where bookId = ?", 
				new Object[]{book.getTitle(),book.getPublisher().getPublisherId(),book.getBookId()} );
	}

	public void deleteBook(Book book) throws ClassNotFoundException, SQLException {
		save("delete from tbl_book where bookId = ?", new Object[] {book.getBookId()});
	}
	
	public List<Book> readBooks() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_book", null);
	}
	
	public Book readBookById(Integer bookId) throws ClassNotFoundException, SQLException {
		return read("select * from tbl_book where bookId = ?", new Object[] {bookId}).get(0);
	}
	
	public void insertBookAuthors(Author author, Book book) throws ClassNotFoundException, SQLException{
		save("insert into tbl_book_authors values(?, ?)", new Object[] {book.getBookId(),author.getAuthorId()});
	}
	
	public void deleteBookAuthors(Book book) throws ClassNotFoundException, SQLException{
		save("delete from tbl_book_authors where bookId = ?", new Object[] {book.getBookId()});
	}
	
	public void insertBookGenres(Genre genre, Book book) throws ClassNotFoundException, SQLException{
		save("insert into tbl_book_genres values(?, ?)", new Object[] {genre.getGenreId(), book.getBookId()});
	}
	
	public void deleteBookGenres(Book book) throws ClassNotFoundException, SQLException{
		save("delete from tbl_book_genres where bookId = ?", new Object[] {book.getBookId()});
	}

	@Override
	List<Book> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Book> books = new ArrayList<>();
		AuthorDAO adao = new AuthorDAO();
		GenreDAO gdao = new GenreDAO();
		PublisherDAO pdao = new PublisherDAO();
		while(rs.next()){
			Book b = new Book();
			b.setBookId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));
			b.setPublisher(pdao.readPublisherById(rs.getInt("pubId")));
			b.setAuthors(adao.readFirstLevel("select * from tbl_author where authorId in"
					+ "(select authorId from tbl_book_authors where bookId = ?)", new Object[] {b.getBookId()}));
			b.setGenres(gdao.readFirstLevel("select * from tbl_genre where genre_id in"
					+ "(select genre_id from tbl_book_genres where bookId = ?)", new Object[] {b.getBookId()}));
			books.add(b);
		}
		return books;
	}

	@Override
	List<Book> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Book> books = new ArrayList<>();
		while(rs.next()){
			Book b = new Book();
			b.setBookId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));
			b.setPublisher(new Publisher());
			b.getPublisher().setPublisherId(rs.getInt("pubId"));
			books.add(b);
		}
		return books;
	}

}
