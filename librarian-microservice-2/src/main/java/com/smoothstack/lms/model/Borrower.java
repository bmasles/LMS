package com.smoothstack.lms.model;

import java.io.Serializable;
import java.util.List;

public class Borrower implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer cardNumber;
	private String name;
	private String address;
	private String phone;
	private List<Book> books;

	public Integer getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(Integer cardNumber) {
		this.cardNumber = cardNumber;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	@Override
	public String toString() {
		return "Borrower [cardNumber=" + cardNumber + ", name=" + name + ", address=" + address + ", phone=" + phone
				+ ", books=" + books + "]";
	}

}