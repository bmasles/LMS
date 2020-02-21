package com.smoothstack.lms.adminmicroservice.dao;

public class NotFound extends Exception {
	private static final long serialVersionUID = -3668268901179169409L;
	String s;

	public NotFound(String s) {
		super(s);
		this.s = s;
	}

	@Override
	public String toString() {
		return (s + " could not be found in the database");
	}
}
