package com.smoothstack.lms.adminmicroservice.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.smoothstack.lms.adminmicroservice.model.Borrower;

@Component
public class BorrowerDAO extends BaseDAO<Borrower> {

	public BorrowerDAO() throws ClassNotFoundException, SQLException {
		super();
	}

	public Integer addBorrower(Borrower borrower) throws ClassNotFoundException, SQLException {
		return save("insert into tbl_borrower (name,address,phone) values (?,?,?)",
				new Object[] { borrower.getName(), borrower.getAddress(), borrower.getPhone() });
	}

	public void updateBorrower(Borrower borrower) throws SQLException, ClassNotFoundException {
		save("update tbl_borrower set name = ?, address = ?, phone = ? where cardNo = ?",
				new Object[] { borrower.getName(), borrower.getAddress(), borrower.getPhone(), borrower.getCardNo() });
	}

	public void deleteBorrower(Borrower borrower) throws ClassNotFoundException, SQLException {
		save("delete from tbl_borrower where cardNo = ?", new Object[] { borrower.getCardNo() });
	}

	public void deleteBorrowerLoans(Borrower borrower) throws ClassNotFoundException, SQLException {
		save("delete from tbl_book_loans where cardNo = ?", new Object[] { borrower.getCardNo() });
	}

	public List<Borrower> readBorrower() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_borrower", null);
	}
	
	public Borrower readBorrowerById(Integer cardNo) throws ClassNotFoundException, SQLException {
		return read("select * from tbl_borrower where cardNo = ?", new Object[] {cardNo}).get(0);
	}

	@Override
	List<Borrower> extractData(ResultSet rs) throws SQLException {
		List<Borrower> borrowers = new ArrayList<>();
		while (rs.next()) {
			Borrower b = new Borrower();
			b.setName(rs.getString("name"));
			b.setAddress(rs.getString("address"));
			b.setCardNo(rs.getInt("cardNo"));
			b.setPhone(rs.getString("phone"));
			borrowers.add(b);
		}
		return borrowers;
	}

	@Override
	List<Borrower> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		return extractData(rs);
	}

}
