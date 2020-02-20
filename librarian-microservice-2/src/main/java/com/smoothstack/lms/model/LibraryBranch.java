package com.smoothstack.lms.model;

import java.io.Serializable;
import java.util.List;

public class LibraryBranch implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String address;
	private List<Book> books;
	private List<Borrower> borrowers;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public List<Borrower> getBorrowers() {
		return borrowers;
	}

	public void setBorrowers(List<Borrower> borrowers) {
		this.borrowers = borrowers;
	}

	@Override
	public String toString() {
		return "LibraryBranch [id=" + id + ", name=" + name + ", address=" + address + ", books=" + books
				+ ", borrowers=" + borrowers + "]";
	}

}
