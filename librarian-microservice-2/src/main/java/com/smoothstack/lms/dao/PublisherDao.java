package com.smoothstack.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.smoothstack.lms.model.Publisher;

@Component
public class PublisherDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Publisher readOneFirstLevel(int id) throws SQLException {
		return jdbcTemplate.queryForObject("select * from tbl_publisher where tbl_publisher.publisherId = ?",
				new Object[] { id }, (rs, rowNum) -> extractDataFirstLevel(rs));
	}

	private Publisher extractDataFirstLevel(ResultSet rs) throws SQLException {
		Publisher publisher = new Publisher();
		publisher.setId(rs.getInt("publisherId"));
		publisher.setName(rs.getString("publisherName"));
		publisher.setAddress(rs.getString("publisherAddress"));
		publisher.setPhone(rs.getString("publisherPhone"));
		return publisher;
	}

}