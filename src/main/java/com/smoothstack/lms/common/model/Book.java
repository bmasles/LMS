package com.smoothstack.lms.common.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "t_book")
@Access(AccessType.FIELD)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "bookId")
public class Book implements Serializable {

	@Id
	@Column(name = "bookId")
	@SequenceGenerator(name = "book", sequenceName = "bookId", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "book")
	private long bookId;

	@Column(name = "bookTitle", nullable = false)
	@NotNull
	@NotBlank
	private String bookTitle;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.DETACH }, optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "publisherId", referencedColumnName = "publisherId")
	private Publisher publisher;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH }, fetch = FetchType.LAZY)
	@JoinTable(name = "r_book_author", joinColumns = @JoinColumn(name = "bookId", referencedColumnName = "bookId"), inverseJoinColumns = @JoinColumn(name = "authorId", referencedColumnName = "authorId"))
	private Set<Author> bookAuthorSet = new HashSet<>();

	@ManyToMany(mappedBy = "genreBookSet")
	private Set<Genre> bookGenreSet = new HashSet<>();

	public Book() {
	}

	public Book(@NotNull @NotBlank String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public long getBookId() {
		return bookId;
	}

	public void setBookId(long bookId) {
		this.bookId = bookId;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public Set<Author> getBookAuthorSet() {
		return bookAuthorSet;
	}

	public void setBookAuthorSet(Set<Author> bookAuthorSet) {
		this.bookAuthorSet = bookAuthorSet;
	}

	public Set<Genre> getBookGenreSet() {
		return bookGenreSet;
	}

	public void setBookGenreSet(Set<Genre> bookGenreSet) {
		this.bookGenreSet = bookGenreSet;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Book book = (Book) o;
		return getBookId() == book.getBookId() && Objects.equals(getBookTitle(), book.getBookTitle())
				&& Objects.equals(getPublisher(), book.getPublisher())
				&& Objects.equals(getBookAuthorSet(), book.getBookAuthorSet())
				&& Objects.equals(getBookGenreSet(), book.getBookGenreSet());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getBookId(), getBookTitle(), getPublisher().getPublisherId(),
				getBookAuthorSet().stream().map(Author::getAuthorId).collect(Collectors.toList()),
				getBookGenreSet().stream().map(Genre::getGenreId).collect(Collectors.toList()));
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", Book.class.getSimpleName() + "[", "]").add("id=" + bookId)
				.add("title='" + bookTitle + "'").add("publisherId=" + publisher.getPublisherId())
				.add("authorSet=" + bookAuthorSet.stream().map(author -> Long.toString(author.getAuthorId()))
						.collect(Collectors.joining(",", "[", "]")))
				.add("genreSet=" + bookGenreSet.stream().map(genre -> Long.toString(genre.getGenreId()))
						.collect(Collectors.joining(",", "[", "]")))
				.toString();
	}
}