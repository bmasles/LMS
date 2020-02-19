package com.smoothstack.lms.adminmicroservice.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.smoothstack.lms.adminmicroservice.model.Author;
import com.smoothstack.lms.adminmicroservice.model.Book;

@Component
public class AuthorDAO extends BaseDAO<Author> {

	public AuthorDAO() throws ClassNotFoundException, SQLException {
		super();
	}

	public Integer addAuthor(Author author) throws ClassNotFoundException, SQLException {
		return save("insert into tbl_author (authorName) values (?)", new Object[] { author.getAuthorName() });
	}

	public void updateAuthor(Author author) throws SQLException, ClassNotFoundException {
		save("update tbl_author set authorName = ? where authorId = ?",
				new Object[] { author.getAuthorName(), author.getAuthorId() });
	}

	public void deleteAuthor(Author author) throws ClassNotFoundException, SQLException {
		save("delete from tbl_author where authorId = ?", new Object[] { author.getAuthorId() });
	}

	public void insertBookAuthors(Author author, Book book) throws ClassNotFoundException, SQLException {
		save("insert into tbl_book_authors values(?, ?)", new Object[] { book.getBookId(), author.getAuthorId() });
	}

	public void deleteBookAuthors(Author author) throws ClassNotFoundException, SQLException {
		save("delete from tbl_book_authors where authorId = ?", new Object[] { author.getAuthorId() });
	}

	public List<Author> readAuthors() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_author", null);
	}

	public List<Author> readAuthorsByName(String authorName) throws ClassNotFoundException, SQLException {
		return read("select * from tbl_author where authorName = ?", new Object[] { authorName });
	}
	
	public Author readAuthorById(Integer authorId) throws ClassNotFoundException, SQLException {
		return read("select * from tbl_author where authorId = ?", new Object[] {authorId}).get(0);
	}

	@Override
	public List<Author> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Author> authors = new ArrayList<>();
		BookDAO bdao = new BookDAO();
		while (rs.next()) {
			Author a = new Author();
			a.setAuthorId(rs.getInt("authorId"));
			a.setAuthorName(rs.getString("authorName"));
			a.setBooks(bdao.readFirstLevel(
					"select * from tbl_book where bookId in "
							+ "(select bookId from tbl_book_authors where authorId = ?)",
					new Object[] { a.getAuthorId() }));
			authors.add(a);
		}
		return authors;
	}

	@Override
	List<Author> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Author> authors = new ArrayList<>();
		while (rs.next()) {
			Author a = new Author();
			a.setAuthorId(rs.getInt("authorId"));
			a.setAuthorName(rs.getString("authorName"));
			authors.add(a);
		}
		return authors;
	}

}
