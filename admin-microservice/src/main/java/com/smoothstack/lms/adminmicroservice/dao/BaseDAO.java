package com.smoothstack.lms.adminmicroservice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.smoothstack.lms.adminmicroservice.util.ConnectionFactory;

public abstract class BaseDAO<T> {
	
	protected Connection conn = null;
	private ApplicationContext ctx;
	
	public BaseDAO() throws ClassNotFoundException, SQLException{
		ctx = new AnnotationConfigApplicationContext(ConnectionFactory.class);
		conn = ctx.getBean(Connection.class);
	}
	
	protected Integer save(String sql, Object[] vals) throws SQLException, ClassNotFoundException{
		PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		if(vals!=null){
			int index = 1;
			for(Object o: vals){
				pstmt.setObject(index, o);
				index++;
			}
		}
		pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		while(rs.next()){
			return rs.getInt(1);
		}
		return null;
	}
	
	protected List<T> read(String sql, Object[] vals) throws SQLException, ClassNotFoundException{
		PreparedStatement pstmt = conn.prepareStatement(sql);
		if(vals!=null){
			int index = 1;
			for(Object o: vals){
				pstmt.setObject(index, o);
				index++;
			}
		}
		ResultSet rs = pstmt.executeQuery();
		return extractData(rs);
	}
	
	protected List<T> readFirstLevel(String sql, Object[] vals) throws SQLException, ClassNotFoundException{
		PreparedStatement pstmt = conn.prepareStatement(sql);
		if(vals!=null){
			int index = 1;
			for(Object o: vals){
				pstmt.setObject(index, o);
				index++;
			}
		}
		ResultSet rs = pstmt.executeQuery();
		return extractDataFirstLevel(rs);
	}
	
	public void commit() throws SQLException {
		conn.commit();
	}
	
	abstract List<T> extractData(ResultSet rs) throws SQLException, ClassNotFoundException;
	
	abstract List<T> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException;

}
