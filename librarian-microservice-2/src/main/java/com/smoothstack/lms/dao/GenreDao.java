package com.smoothstack.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.smoothstack.lms.model.Genre;

@Component
public class GenreDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Genre> readFirstLevel(String sql, Object[] values) throws SQLException {
		return jdbcTemplate.query(sql, values, (rs, rowNum) -> extractDataFirstLevel(rs));
	}

	private Genre extractDataFirstLevel(ResultSet rs) throws SQLException {
		Genre genre = new Genre();
		genre.setId(rs.getInt("genre_id"));
		genre.setName(rs.getString("genre_name"));
		return genre;
	}
}