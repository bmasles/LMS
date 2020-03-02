package com.smoothstack.lms.common.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "t_book_copies")
@Access(AccessType.FIELD)
@IdClass(BookBranch.class)
public class Copies implements Serializable {

	@Id
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "bookId", referencedColumnName = "bookId", nullable = false)
	@NotNull
	private Book book;

	@Id
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "branchId", referencedColumnName = "branchId", nullable = false)
	@NotNull
	private Branch branch;

	@Column(name = "copiesAmount")
	@Min(0)
	private int copiesAmount;

	public Copies() {
	}

	public Copies(Book book, Branch branch, int copiesAmount) {
		this.book = book;
		this.branch = branch;
		this.copiesAmount = copiesAmount;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public int getCopiesAmount() {
		return copiesAmount;
	}

	public void setCopiesAmount(int copiesAmount) {
		this.copiesAmount = copiesAmount;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Copies copies = (Copies) o;
		return getCopiesAmount() == copies.getCopiesAmount() && getBook().equals(copies.getBook())
				&& getBranch().equals(copies.getBranch());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getBook().getBookId(), getBranch().getBranchId(), getCopiesAmount());
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", Copies.class.getSimpleName() + "[", "]").add("bookId=" + book.getBookId())
				.add("branchId=" + branch.getBranchId()).add("amount=" + copiesAmount).toString();
	}
}