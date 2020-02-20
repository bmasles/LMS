package com.smoothstack.lms;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.smoothstack.lms.model.Author;
import com.smoothstack.lms.model.Book;
import com.smoothstack.lms.model.BookCopy;
import com.smoothstack.lms.model.Borrower;
import com.smoothstack.lms.model.Genre;
import com.smoothstack.lms.model.LibraryBranch;
import com.smoothstack.lms.model.Publisher;

public class ModelTests {

	@Test
	public void author() {
		Author author = new Author();
		author.setId(0);
		author.setName("");
		author.setBooks(new ArrayList<>());
		author.getId();
		author.getName();
		author.getBooks();
		author.toString();
	}

	@Test
	public void book() {
		Book book = new Book();
		book.setId(0);
		book.setTitle("");
		book.setPublisher(new Publisher());
		book.setAuthors(new ArrayList<>());
		book.setGenres(new ArrayList<>());
		book.setLibraryBranches(new ArrayList<>());
		book.setBorrowers(new ArrayList<>());
		book.getId();
		book.getTitle();
		book.getPublisher();
		book.getAuthors();
		book.getGenres();
		book.getLibraryBranches();
		book.getBorrowers();
		book.toString();
	}

	@Test
	public void bookCopy() {
		BookCopy bookCopy = new BookCopy();
		bookCopy.setBook(new Book());
		bookCopy.setLibraryBranch(new LibraryBranch());
		bookCopy.setAmount(0);
		bookCopy.getBook();
		bookCopy.getLibraryBranch();
		bookCopy.getAmount();
		bookCopy.toString();
	}

	@Test
	public void borrower() {
		Borrower borrower = new Borrower();
		borrower.setCardNumber(0);
		borrower.setName("");
		borrower.setAddress("");
		borrower.setPhone("");
		borrower.setBooks(new ArrayList<>());
		borrower.getCardNumber();
		borrower.getName();
		borrower.getAddress();
		borrower.getPhone();
		borrower.getBooks();
		borrower.toString();
	}

	@Test
	public void genre() {
		Genre genre = new Genre();
		genre.setId(0);
		genre.setName("");
		genre.setBooks(new ArrayList<>());
		genre.getId();
		genre.getName();
		genre.getBooks();
		genre.toString();
	}

	@Test
	public void libraryBranch() {
		LibraryBranch libraryBranch = new LibraryBranch();
		libraryBranch.setId(0);
		libraryBranch.setName("");
		libraryBranch.setAddress("");
		libraryBranch.setBooks(new ArrayList<>());
		libraryBranch.setBorrowers(new ArrayList<>());
		libraryBranch.getId();
		libraryBranch.getName();
		libraryBranch.getAddress();
		libraryBranch.getBooks();
		libraryBranch.getBorrowers();
		libraryBranch.toString();
	}

	@Test
	public void publisher() {
		Publisher publisher = new Publisher();
		publisher.setId(0);
		publisher.setName("");
		publisher.setAddress("");
		publisher.setPhone("");
		publisher.setBooks(new ArrayList<>());
		publisher.getId();
		publisher.getName();
		publisher.getAddress();
		publisher.getPhone();
		publisher.getBooks();
		publisher.toString();
	}

}
