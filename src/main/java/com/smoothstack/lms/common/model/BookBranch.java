package com.smoothstack.lms.common.model;

import java.io.Serializable;

public class BookBranch implements Serializable {

	private long book;
	private long branch;

	public BookBranch() {

	}

	public BookBranch(long book, long branch) {
		this.book = book;
		this.branch = branch;
	}

}
