package com.smoothstack.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.smoothstack.lms.model.Author;

@Component
public class AuthorDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Author> readFirstLevel(String sql, Object[] values) throws SQLException {
		return jdbcTemplate.query(sql, values, (rs, rowNum) -> extractDataFirstLevel(rs));
	}

	private Author extractDataFirstLevel(ResultSet rs) throws SQLException {
		Author author = new Author();
		author.setId(rs.getInt("authorId"));
		author.setName(rs.getString("authorName"));
		return author;
	}

}