package com.smoothstack.lms;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.smoothstack.lms.controller.LibrarianController;
import com.smoothstack.lms.model.Book;
import com.smoothstack.lms.model.BookCopy;
import com.smoothstack.lms.model.LibraryBranch;

@SpringBootTest
class LibrarianControllerTests {

	@Autowired
	private LibrarianController librarianController;

	@Test
	void getLibraryBranches() {
		Assertions.assertEquals(HttpStatus.OK, librarianController.getLibraryBranches().getStatusCode());
	}

	@Test
	void getLibraryBranchByValidId() {
		Assertions.assertEquals(HttpStatus.OK, librarianController.getLibraryBranchById("1").getStatusCode());
	}

	@Test
	void getLibraryBranchByNullId() {
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, librarianController.getLibraryBranchById(null).getStatusCode());
	}

	@Test
	void getLibraryBranchByLetterId() {
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, librarianController.getLibraryBranchById("a").getStatusCode());
	}

	@Test
	void getLibraryBranchByNumberId() {
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, librarianController.getLibraryBranchById("0").getStatusCode());
	}

	@Test
	void updateLibraryBranch() {
		LibraryBranch libraryBranch = new LibraryBranch();
		libraryBranch.setId(1);
		libraryBranch.setName("TestName");
		libraryBranch.setAddress("TestAddress");
		Assertions.assertEquals(HttpStatus.OK, librarianController.updateLibraryBranch(libraryBranch).getStatusCode());
	}

	@Test
	void updateLibraryBranchNull() {
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, librarianController.updateLibraryBranch(null).getStatusCode());
	}

	@Test
	void updateLibraryBranchNullId() {
		LibraryBranch libraryBranch = new LibraryBranch();
		libraryBranch.setId(null);
		libraryBranch.setName("TestName");
		libraryBranch.setAddress("TestAddress");
		Assertions.assertEquals(HttpStatus.BAD_REQUEST,
				librarianController.updateLibraryBranch(libraryBranch).getStatusCode());
	}

	@Test
	void updateLibraryBranchInvalidId() {
		LibraryBranch libraryBranch = new LibraryBranch();
		libraryBranch.setId(0);
		libraryBranch.setName("TestName");
		libraryBranch.setAddress("TestAddress");
		Assertions.assertEquals(HttpStatus.BAD_REQUEST,
				librarianController.updateLibraryBranch(libraryBranch).getStatusCode());
	}

	@Test
	void updateLibraryBranchNullName() {
		LibraryBranch libraryBranch = new LibraryBranch();
		libraryBranch.setId(1);
		libraryBranch.setName(null);
		libraryBranch.setAddress("TestAddress");
		Assertions.assertEquals(HttpStatus.BAD_REQUEST,
				librarianController.updateLibraryBranch(libraryBranch).getStatusCode());
	}

	@Test
	void updateLibraryBranchEmptyName() {
		LibraryBranch libraryBranch = new LibraryBranch();
		libraryBranch.setId(1);
		libraryBranch.setName("");
		libraryBranch.setAddress("TestAddress");
		Assertions.assertEquals(HttpStatus.BAD_REQUEST,
				librarianController.updateLibraryBranch(libraryBranch).getStatusCode());
	}

	@Test
	void updateLibraryBranchNullAddress() {
		LibraryBranch libraryBranch = new LibraryBranch();
		libraryBranch.setId(1);
		libraryBranch.setName("TestName");
		libraryBranch.setAddress(null);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST,
				librarianController.updateLibraryBranch(libraryBranch).getStatusCode());
	}

	@Test
	void updateLibraryBranchEmptyAddress() {
		LibraryBranch libraryBranch = new LibraryBranch();
		libraryBranch.setId(1);
		libraryBranch.setName("TestName");
		libraryBranch.setAddress("");
		Assertions.assertEquals(HttpStatus.BAD_REQUEST,
				librarianController.updateLibraryBranch(libraryBranch).getStatusCode());
	}

	@Test
	void getBooks() {
		Assertions.assertEquals(HttpStatus.OK, librarianController.getBooks().getStatusCode());
	}

	@Test
	void getBookCopy() {
		Assertions.assertEquals(HttpStatus.OK, librarianController.getBookCopy("1", "1").getStatusCode());
	}

	@Test
	void getBookCopyNullBookId() {
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, librarianController.getBookCopy(null, "1").getStatusCode());
	}

	@Test
	void getBookCopyLetterBookId() {
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, librarianController.getBookCopy("a", "1").getStatusCode());
	}

	@Test
	void getBookCopyNumberBookId() {
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, librarianController.getBookCopy("0", "1").getStatusCode());
	}

	@Test
	void getBookCopyNullLibraryBranchId() {
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, librarianController.getBookCopy("1", null).getStatusCode());
	}

	@Test
	void getBookCopyLetterLibraryBranchId() {
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, librarianController.getBookCopy("1", "a").getStatusCode());
	}

	@Test
	void getBookCopyNumberLibraryBranchId() {
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, librarianController.getBookCopy("1", "0").getStatusCode());
	}

	@Test
	void updateBookCopy() {
		BookCopy bookCopy = new BookCopy();
		Book book = new Book();
		book.setId(1);
		bookCopy.setBook(book);
		LibraryBranch libraryBranch = new LibraryBranch();
		libraryBranch.setId(1);
		bookCopy.setLibraryBranch(libraryBranch);
		bookCopy.setAmount(777);
		Assertions.assertEquals(HttpStatus.OK, librarianController.updateBookCopy(bookCopy).getStatusCode());
		;
	}

	@Test
	void updateBookCopyNull() {
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, librarianController.updateBookCopy(null).getStatusCode());
	}

	@Test
	void updateBookCopyNullBook() {
		BookCopy bookCopy = new BookCopy();
		bookCopy.setBook(null);
		LibraryBranch libraryBranch = new LibraryBranch();
		libraryBranch.setId(1);
		bookCopy.setLibraryBranch(libraryBranch);
		bookCopy.setAmount(777);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, librarianController.updateBookCopy(bookCopy).getStatusCode());
	}

	@Test
	void updateBookCopyNullBookId() {
		BookCopy bookCopy = new BookCopy();
		Book book = new Book();
		book.setId(null);
		bookCopy.setBook(book);
		LibraryBranch libraryBranch = new LibraryBranch();
		libraryBranch.setId(1);
		bookCopy.setLibraryBranch(libraryBranch);
		bookCopy.setAmount(777);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, librarianController.updateBookCopy(bookCopy).getStatusCode());
	}

	@Test
	void updateBookCopyNegativeBookId() {
		BookCopy bookCopy = new BookCopy();
		Book book = new Book();
		book.setId(-1);
		bookCopy.setBook(book);
		LibraryBranch libraryBranch = new LibraryBranch();
		libraryBranch.setId(1);
		bookCopy.setLibraryBranch(libraryBranch);
		bookCopy.setAmount(777);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, librarianController.updateBookCopy(bookCopy).getStatusCode());
	}

	@Test
	void updateBookCopyNullLibraryBranch() {
		BookCopy bookCopy = new BookCopy();
		Book book = new Book();
		book.setId(1);
		bookCopy.setBook(book);
		LibraryBranch libraryBranch = new LibraryBranch();
		libraryBranch.setId(1);
		bookCopy.setLibraryBranch(null);
		bookCopy.setAmount(777);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, librarianController.updateBookCopy(bookCopy).getStatusCode());
	}

	@Test
	void updateBookCopyNullLibraryBranchId() {
		BookCopy bookCopy = new BookCopy();
		Book book = new Book();
		book.setId(1);
		bookCopy.setBook(book);
		LibraryBranch libraryBranch = new LibraryBranch();
		libraryBranch.setId(null);
		bookCopy.setLibraryBranch(libraryBranch);
		bookCopy.setAmount(777);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, librarianController.updateBookCopy(bookCopy).getStatusCode());
	}

	@Test
	void updateBookCopyNegativeLibraryBranchId() {
		BookCopy bookCopy = new BookCopy();
		Book book = new Book();
		book.setId(1);
		bookCopy.setBook(book);
		LibraryBranch libraryBranch = new LibraryBranch();
		libraryBranch.setId(-1);
		bookCopy.setLibraryBranch(libraryBranch);
		bookCopy.setAmount(777);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, librarianController.updateBookCopy(bookCopy).getStatusCode());
	}

	@Test
	void updateBookCopyNullAmount() {
		BookCopy bookCopy = new BookCopy();
		Book book = new Book();
		book.setId(1);
		bookCopy.setBook(book);
		LibraryBranch libraryBranch = new LibraryBranch();
		libraryBranch.setId(1);
		bookCopy.setLibraryBranch(libraryBranch);
		bookCopy.setAmount(null);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, librarianController.updateBookCopy(bookCopy).getStatusCode());
	}

	@Test
	void updateBookCopyNegativeAmount() {
		BookCopy bookCopy = new BookCopy();
		Book book = new Book();
		book.setId(1);
		bookCopy.setBook(book);
		LibraryBranch libraryBranch = new LibraryBranch();
		libraryBranch.setId(1);
		bookCopy.setLibraryBranch(libraryBranch);
		bookCopy.setAmount(-1);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, librarianController.updateBookCopy(bookCopy).getStatusCode());
	}
}