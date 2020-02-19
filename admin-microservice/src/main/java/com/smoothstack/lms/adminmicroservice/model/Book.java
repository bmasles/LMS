package com.smoothstack.lms.adminmicroservice.model;

import java.util.List;

public class Book {
	private Integer bookId;
	private String title;
	private Publisher publisher;
	private List<Author> authors;
	private List<Genre> genres;

	public Integer getBookId() {
		return bookId;
	}

	public List<Genre> getGenres() {
		return genres;
	}

	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", title=" + title + ", publisher=" + publisher + ", authors=" + authors
				+ ", genres=" + genres + "]";
	}

}
