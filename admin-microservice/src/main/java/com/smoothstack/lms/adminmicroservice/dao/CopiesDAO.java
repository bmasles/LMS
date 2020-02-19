package com.smoothstack.lms.adminmicroservice.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.smoothstack.lms.adminmicroservice.model.Copies;

@Component
public class CopiesDAO extends BaseDAO<Copies> {
	
	public CopiesDAO() throws ClassNotFoundException, SQLException {
		super();
	}

	public void addCopies(Copies copies) throws ClassNotFoundException, SQLException {
		save("insert into tbl_book_copies (branchId,bookId,noOfCopies) values (?,?,?)", 
				new Object[] {copies.getBranchId(),copies.getBookId(),copies.getNoOfCopies()});
	}

	public void updateCopies(Copies copies) throws SQLException, ClassNotFoundException {
		save("update tbl_book_copies set noOfCopies = ? where bookId = ? and branchId = ?", 
				new Object[]{copies.getNoOfCopies(),copies.getBookId(),copies.getBranchId()} );
	}

	public void deleteCopies(Copies copies) throws ClassNotFoundException, SQLException {
		save("delete from tbl_book_copies where bookId = ? and branchId = ?"
				,new Object[] {copies.getBookId(),copies.getBranchId()});
	}
	
	public void deleteCopiesByBookId(Integer bookId) throws ClassNotFoundException, SQLException {
		save("delete from tbl_book_copies where bookId = ?"
				, new Object[] {bookId});
	}
	
	public void deleteCopiesByBranchId(Integer bookId) throws ClassNotFoundException, SQLException {
		save("delete from tbl_book_copies where branchId = ?"
				, new Object[] {bookId});
	}
	
	public List<Copies> readCopies() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_book_copies", null);
	}
	
	public List<Copies> readCopyById(Integer branchId) throws ClassNotFoundException, SQLException {
		return read("select * from tbl_book_copies where branchId = ?",
				new Object[] {branchId});
	}
	
	public List<Copies> readCopyById(Integer branchId, Integer bookId) throws ClassNotFoundException, SQLException {
		return read("select * from tbl_book_copies where branchId = ? and bookId = ?",
				new Object[] {branchId,bookId});
	}

	@Override
	List<Copies> extractData(ResultSet rs) throws SQLException {
		List<Copies> copies = new ArrayList<Copies>();
		while (rs.next()) {
			Copies copy = new Copies();
			copy.setBookId(rs.getInt("bookId"));
			copy.setBranchId(rs.getInt("branchId"));
			copy.setNoOfCopies(rs.getInt("noOfCopies"));
			copies.add(copy);
		}
		return copies;
	}

	@Override
	List<Copies> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		return extractData(rs);
	}

}
