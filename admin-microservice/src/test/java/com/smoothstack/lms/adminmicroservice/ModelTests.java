package com.smoothstack.lms.adminmicroservice;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.smoothstack.lms.adminmicroservice.model.Author;
import com.smoothstack.lms.adminmicroservice.model.Book;
import com.smoothstack.lms.adminmicroservice.model.Borrower;
import com.smoothstack.lms.adminmicroservice.model.Branch;
import com.smoothstack.lms.adminmicroservice.model.Copies;
import com.smoothstack.lms.adminmicroservice.model.Genre;
import com.smoothstack.lms.adminmicroservice.model.Publisher;

@SpringBootTest
class ModelTests {

	@Test
	void authorTest() {
		Author author = new Author();
		author.setAuthorId(0);
		author.setAuthorName("");
		author.setBooks(new ArrayList<Book>());
		author.getAuthorId();
		author.getAuthorName();
		author.getBooks();
		author.toString();
	}

	@Test
	void bookTest() {
		Book book = new Book();
		book.setAuthors(new ArrayList<Author>());
		book.setBookId(0);
		book.setGenres(new ArrayList<Genre>());
		book.setPublisher(new Publisher());
		book.setTitle("");
		book.getAuthors();
		book.getBookId();
		book.getGenres();
		book.getPublisher();
		book.getTitle();
		book.toString();
	}

	@Test
	void borrowerTest() {
		Borrower borrower = new Borrower();
		borrower.setAddress("");
		borrower.setCardNo(0);
		borrower.setName("");
		borrower.setPhone("");
		borrower.getAddress();
		borrower.getCardNo();
		borrower.getName();
		borrower.getPhone();
		borrower.toString();
	}

	@Test
	void branchTest() {
		Branch branch = new Branch();
		branch.setBranchAddress("");
		branch.setBranchId(0);
		branch.setBranchName("");
		branch.getBranchAddress();
		branch.getBranchId();
		branch.getBranchName();
		branch.toString();
	}

	@Test
	void copiesTest() {
		Copies copy = new Copies();
		copy.setBookId(0);
		copy.setBranchId(0);
		copy.setNoOfCopies(0);
		copy.getBookId();
		copy.getBranchId();
		copy.getNoOfCopies();
		copy.toString();
	}

	@Test
	void genreTest() {
		Genre genre = new Genre();
		genre.setBooks(new ArrayList<Book>());
		genre.setGenreId(0);
		genre.setGenreName("");
		genre.getBooks();
		genre.getGenreId();
		genre.getGenreName();
		genre.toString();
	}

	@Test
	void publisherTest() {
		Publisher publisher = new Publisher();
		publisher.setPublisherAddress("");
		publisher.setPublisherId(0);
		publisher.setPublisherName("");
		publisher.setPublisherPhone("");
		publisher.getPublisherAddress();
		publisher.getPublisherId();
		publisher.getPublisherName();
		publisher.getPublisherPhone();
		publisher.toString();
	}
}
