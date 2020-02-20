package com.smoothstack.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.smoothstack.lms.model.LibraryBranch;

@Component
public class LibraryBranchDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private BookDao bookDao;

	@Autowired
	private BorrowerDao borrowerDao;

	public List<LibraryBranch> read() throws SQLException {
		return jdbcTemplate.query("select * from tbl_library_branch", (rs, rowNum) -> extractData(rs));
	}

	public List<LibraryBranch> readFirstLevel(String sql, Object[] values) throws SQLException {
		return jdbcTemplate.query(sql, values, (rs, rowNum) -> extractDataFirstLevel(rs));
	}

	public LibraryBranch readOne(int id) throws SQLException {
		return jdbcTemplate.queryForObject("select * from tbl_library_branch where tbl_library_branch.branchId = ?",
				new Object[] { id }, (rs, rowNum) -> extractData(rs));
	}

	public LibraryBranch readOneFirstLevel(int id) throws SQLException {
		return jdbcTemplate.queryForObject("select * from tbl_library_branch where tbl_library_branch.branchId = ?",
				new Object[] { id }, (rs, rowNum) -> extractDataFirstLevel(rs));
	}

	public int update(LibraryBranch libraryBranch) throws SQLException {
		return jdbcTemplate.update("update tbl_library_branch set branchName = ?, branchAddress = ? where branchId = ?",
				new Object[] { libraryBranch.getName().trim(), libraryBranch.getAddress().trim(),
						libraryBranch.getId() });
	}

	private LibraryBranch extractData(ResultSet rs) throws SQLException {
		LibraryBranch libraryBranch = new LibraryBranch();
		libraryBranch.setId(rs.getInt("branchId"));
		libraryBranch.setName(rs.getString("branchName"));
		libraryBranch.setAddress(rs.getString("branchAddress"));
		libraryBranch.setBooks(bookDao.readFirstLevel(
				"select * from tbl_book inner join tbl_book_copies on tbl_book.bookId = tbl_book_copies.bookId where tbl_book_copies.branchId = ?",
				new Object[] { rs.getInt("branchId") }));
		libraryBranch.setBorrowers(borrowerDao.readFirstLevel(
				"select * from tbl_borrower inner join tbl_book_loans on tbl_borrower.cardNo = tbl_book_loans.cardNo where tbl_book_loans.branchId = ?",
				new Object[] { rs.getInt("branchId") }));
		return libraryBranch;
	}

	private LibraryBranch extractDataFirstLevel(ResultSet rs) throws SQLException {
		LibraryBranch libraryBranch = new LibraryBranch();
		libraryBranch.setId(rs.getInt("branchId"));
		libraryBranch.setName(rs.getString("branchName"));
		libraryBranch.setAddress(rs.getString("branchAddress"));
		return libraryBranch;
	}

}