package com.smoothstack.lms.adminmicroservice.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.smoothstack.lms.adminmicroservice.model.Publisher;

@Component
public class PublisherDAO extends BaseDAO<Publisher> {

	public PublisherDAO() throws ClassNotFoundException, SQLException {
		super();
	}

	public Integer addPublisher(Publisher pub) throws ClassNotFoundException, SQLException {
		return save("insert into tbl_publisher (publisherName,publisherAddress,publisherPhone) values (?,?,?)",
				new Object[] { pub.getPublisherName(), pub.getPublisherAddress(), pub.getPublisherPhone() });
	}

	public void updatePublisher(Publisher pub) throws SQLException, ClassNotFoundException {
		save("update tbl_publisher set publisherName = ?, publisherAddress = ?, publisherPhone = ? where publisherId = ?",
				new Object[] { pub.getPublisherName(), pub.getPublisherAddress(), pub.getPublisherPhone(),
						pub.getPublisherId() });
	}

	public void deletePublisher(Publisher pub) throws ClassNotFoundException, SQLException {
		save("delete from tbl_publisher where publisherId = ?", new Object[] { pub.getPublisherId() });
	}

	public void deletePublisherBooks(Publisher pub) throws ClassNotFoundException, SQLException {
		save("delete from tbl_publisher where publisherId = ?", new Object[] { pub.getPublisherId() });
	}

	public List<Publisher> readPublisher() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_publisher", null);
	}

	public Publisher readPublisherById(Integer id) throws ClassNotFoundException, SQLException {
		return read("select * from tbl_publisher where publisherId = ?", new Object[] { id }).get(0);
	}

	@Override
	List<Publisher> extractData(ResultSet rs) throws SQLException {
		List<Publisher> pubs = new ArrayList<Publisher>();
		while (rs.next()) {
			Publisher pub = new Publisher();
			pub.setPublisherId(rs.getInt("publisherId"));
			pub.setPublisherName(rs.getString("publisherName"));
			pub.setPublisherAddress(rs.getString("publisherAddress"));
			pub.setPublisherPhone(rs.getString("publisherPhone"));
			pubs.add(pub);
		}
		return pubs;
	}

	@Override
	List<Publisher> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		return extractData(rs);
	}

}
