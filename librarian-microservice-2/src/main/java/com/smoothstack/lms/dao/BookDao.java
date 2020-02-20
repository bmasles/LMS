package com.smoothstack.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.smoothstack.lms.model.Book;
import com.smoothstack.lms.model.Publisher;

@Component
public class BookDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private PublisherDao publisherDao;

	@Autowired
	private AuthorDao authorDao;

	@Autowired
	private GenreDao genreDao;

	@Autowired
	private LibraryBranchDao libraryBranchDao;

	@Autowired
	private BorrowerDao borrowerDao;

	public List<Book> read() throws SQLException {
		return jdbcTemplate.query("select * from tbl_book", (rs, rowNum) -> extractData(rs));
	}

	public List<Book> readFirstLevel(String sql, Object[] values) throws SQLException {
		return jdbcTemplate.query(sql, values, (rs, rowNum) -> extractDataFirstLevel(rs));
	}

	public Book readOneFirstLevel(int id) throws SQLException {
		return jdbcTemplate.queryForObject("select * from tbl_book where tbl_book.bookId = ?", new Object[] { id },
				(rs, rowNum) -> extractDataFirstLevel(rs));
	}

	private Book extractData(ResultSet rs) throws SQLException {
		Book book = new Book();
		book.setId(rs.getInt("bookId"));
		book.setTitle(rs.getString("title"));
		book.setPublisher(publisherDao.readOneFirstLevel(rs.getInt("pubId")));
		book.setAuthors(authorDao.readFirstLevel(
				"select * from tbl_author inner join tbl_book_authors on tbl_author.authorId = tbl_book_authors.authorId where tbl_book_authors.bookId = ?",
				new Object[] { rs.getInt("bookId") }));
		book.setGenres(genreDao.readFirstLevel(
				"select * from tbl_genre inner join tbl_book_genres on tbl_genre.genre_id = tbl_book_genres.genre_id where tbl_book_genres.bookId = ?",
				new Object[] { rs.getInt("bookId") }));
		book.setLibraryBranches(libraryBranchDao.readFirstLevel(
				"select * from tbl_library_branch inner join tbl_book_loans on tbl_library_branch.branchId = tbl_book_loans.branchId where tbl_book_loans.bookId = ?",
				new Object[] { rs.getInt("bookId") }));
		book.setBorrowers(borrowerDao.readFirstLevel(
				"select * from tbl_borrower inner join tbl_book_loans on tbl_borrower.cardNo = tbl_book_loans.cardNo where tbl_book_loans.bookId = ?",
				new Object[] { rs.getInt("bookId") }));
		return book;
	}

	private Book extractDataFirstLevel(ResultSet rs) throws SQLException {
		Book book = new Book();
		book.setId(rs.getInt("bookId"));
		book.setTitle(rs.getString("title"));
		Publisher publisher = new Publisher();
		publisher.setId(rs.getInt("pubId"));
		book.setPublisher(publisher);
		return book;
	}

}