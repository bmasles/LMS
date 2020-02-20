package com.smoothstack.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.smoothstack.lms.model.Borrower;

@Component
public class BorrowerDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Borrower> readFirstLevel(String sql, Object[] values) throws SQLException {
		return jdbcTemplate.query(sql, values, (rs, rowNum) -> extractDataFirstLevel(rs));
	}

	private Borrower extractDataFirstLevel(ResultSet rs) throws SQLException {
		Borrower borrower = new Borrower();
		borrower.setCardNumber(rs.getInt("cardNo"));
		borrower.setName(rs.getString("name"));
		borrower.setAddress(rs.getString("address"));
		borrower.setPhone(rs.getString("phone"));
		return borrower;
	}

}