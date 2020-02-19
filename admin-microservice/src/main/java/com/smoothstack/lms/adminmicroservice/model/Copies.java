package com.smoothstack.lms.adminmicroservice.model;

public class Copies {
	private Integer bookId;
	private Integer branchId;
	private Integer noOfCopies;

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	public Integer getNoOfCopies() {
		return noOfCopies;
	}

	public void setNoOfCopies(Integer noOfCopies) {
		this.noOfCopies = noOfCopies;
	}

	@Override
	public String toString() {
		return "Copies [bookId=" + bookId + ", branchId=" + branchId + ", noOfCopies=" + noOfCopies + "]";
	}
	
}
