package com.smoothstack.lms.adminmicroservice;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.smoothstack.lms.adminmicroservice.model.Author;
import com.smoothstack.lms.adminmicroservice.model.Book;
import com.smoothstack.lms.adminmicroservice.model.Borrower;
import com.smoothstack.lms.adminmicroservice.model.Branch;
import com.smoothstack.lms.adminmicroservice.model.Copies;
import com.smoothstack.lms.adminmicroservice.model.Genre;
import com.smoothstack.lms.adminmicroservice.model.Publisher;
import com.smoothstack.lms.adminmicroservice.service.AdminService;

@SpringBootTest
class AdminServiceTest {

	@Autowired
	AdminService adminService;

	@Test
	void testAuthor() throws ClassNotFoundException, SQLException {
		Author author = new Author();
		author.setAuthorName("Test");
		author.setBooks(new ArrayList<Book>());
		adminService.saveAuthor(author);
		Assertions.assertNotNull(adminService.readAuthorById(author.getAuthorId()));
		author.setAuthorName("Test2");
		adminService.updateAuthor(author);
		Assertions.assertEquals("Test2", adminService.readAuthorById(author.getAuthorId()).getAuthorName());
		adminService.deleteAuthor(author);
		Assertions.assertThrows(IndexOutOfBoundsException.class,
				() -> adminService.readAuthorById(author.getAuthorId()));
	}

	@Test
	void testReadAuthor() throws ClassNotFoundException, SQLException {
		Assertions.assertNotNull(adminService.readAuthor());
	}

	@Test
	void testReadAuthorById() throws ClassNotFoundException, SQLException {
		Assertions.assertNotNull(adminService.readAuthorById(1));
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> adminService.readAuthorById(0));
	}

	@Test
	void testBook() throws ClassNotFoundException, SQLException {
		Book book = new Book();
		book.setAuthors(new ArrayList<Author>());
		book.setGenres(new ArrayList<Genre>());
		book.setPublisher(new Publisher());
		book.getPublisher().setPublisherId(1);
		book.setTitle("Test");
		adminService.saveBook(book);
		Assertions.assertNotNull(adminService.readBookById(book.getBookId()));
		book.setTitle("Test2");
		adminService.updateBook(book);
		Assertions.assertEquals("Test2", adminService.readBookById(book.getBookId()).getTitle());
		adminService.deleteBook(book);
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> adminService.readBookById(book.getBookId()));
	}

	@Test
	void testReadBook() throws ClassNotFoundException, SQLException {
		Assertions.assertNotNull(adminService.readBook());
	}

	@Test
	void testReadBookById() throws ClassNotFoundException, SQLException {
		Assertions.assertNotNull(adminService.readBookById(1));
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> adminService.readBookById(0));
	}

	@Test
	void testGenre() throws ClassNotFoundException, SQLException {
		Genre genre = new Genre();
		genre.setGenreName("Test");
		genre.setBooks(new ArrayList<Book>());
		adminService.saveGenre(genre);
		Assertions.assertNotNull(adminService.readGenreById(genre.getGenreId()));
		genre.setGenreName("Test2");
		adminService.updateGenre(genre);
		Assertions.assertEquals("Test2", adminService.readGenreById(genre.getGenreId()).getGenreName());
		adminService.deleteGenre(genre);
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> adminService.readGenreById(genre.getGenreId()));
	}

	@Test
	void testReadGenre() throws ClassNotFoundException, SQLException {
		Assertions.assertNotNull(adminService.readGenre());
	}

	@Test
	void testReadGenreById() throws ClassNotFoundException, SQLException {
		Assertions.assertNotNull(adminService.readGenreById(1));
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> adminService.readGenreById(0));
	}

	@Test
	void testBorrower() throws ClassNotFoundException, SQLException {
		Borrower borrower = new Borrower();
		borrower.setName("Test");
		borrower.setAddress("123");
		borrower.setPhone("123");
		adminService.saveBorrower(borrower);
		Assertions.assertNotNull(adminService.readBorrowerById(borrower.getCardNo()));
		borrower.setName("Test2");
		adminService.updateBorrower(borrower);
		Assertions.assertEquals("Test2", adminService.readBorrowerById(borrower.getCardNo()).getName());
		adminService.deleteBorrower(borrower);
		Assertions.assertThrows(IndexOutOfBoundsException.class,
				() -> adminService.readBorrowerById(borrower.getCardNo()));
	}

	@Test
	void testReadBorrower() throws ClassNotFoundException, SQLException {
		Assertions.assertNotNull(adminService.readBorrower());
	}

	@Test
	void testReadBorrowerById() throws ClassNotFoundException, SQLException {
		Assertions.assertNotNull(adminService.readBorrowerById(1));
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> adminService.readBorrowerById(0));
	}

	@Test
	void testBranch() throws ClassNotFoundException, SQLException {
		Branch branch = new Branch();
		branch.setBranchName("Test");
		branch.setBranchAddress("123");
		adminService.saveBranch(branch);
		Assertions.assertNotNull(adminService.readBranchById(branch.getBranchId()));
		branch.setBranchName("Test2");
		adminService.updateBranch(branch);
		Assertions.assertEquals("Test2", adminService.readBranchById(branch.getBranchId()).getBranchName());
		adminService.deleteBranch(branch);
		Assertions.assertThrows(IndexOutOfBoundsException.class,
				() -> adminService.readBranchById(branch.getBranchId()));
	}

	@Test
	void testReadBranch() throws ClassNotFoundException, SQLException {
		Assertions.assertNotNull(adminService.readBranch());
	}

	@Test
	void testReadBranchById() throws ClassNotFoundException, SQLException {
		Assertions.assertNotNull(adminService.readBranchById(1));
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> adminService.readBranchById(0));
	}

	@Test
	void testCopies() throws ClassNotFoundException, SQLException {
		Copies copy = new Copies();
		copy.setBookId(5);
		copy.setBranchId(3);
		copy.setNoOfCopies(66);
		adminService.saveCopies(copy);
		Assertions.assertNotNull(adminService.readCopyById(copy.getBranchId(), copy.getBookId()));
		copy.setNoOfCopies(67);
		adminService.updateCopies(copy);
		Assertions.assertEquals(67, adminService.readCopyById(copy.getBranchId(), copy.getBookId()).getNoOfCopies());
		adminService.deleteCopies(copy);
		Assertions.assertThrows(IndexOutOfBoundsException.class,
				() -> adminService.readCopyById(copy.getBranchId(), copy.getBookId()));
	}

	@Test
	void testReadCopies() throws ClassNotFoundException, SQLException {
		Assertions.assertNotNull(adminService.readCopies());
	}

	@Test
	void testReadCopiesById() throws ClassNotFoundException, SQLException {
		Assertions.assertNotNull(adminService.readCopiesById(1));
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> adminService.readCopiesById(0).get(0));
	}

	@Test
	void testPublisher() throws ClassNotFoundException, SQLException {
		Publisher publisher = new Publisher();
		publisher.setPublisherName("Test");
		publisher.setPublisherAddress("123");
		publisher.setPublisherPhone("123");
		adminService.savePublisher(publisher);
		Assertions.assertNotNull(adminService.readPublisherById(publisher.getPublisherId()));
		publisher.setPublisherName("Test2");
		adminService.updatePublisher(publisher);
		Assertions.assertEquals("Test2", adminService.readPublisherById(publisher.getPublisherId()).getPublisherName());
		adminService.deletePublisher(publisher);
		Assertions.assertThrows(IndexOutOfBoundsException.class,
				() -> adminService.readPublisherById(publisher.getPublisherId()));
	}

	@Test
	void testReadPublisher() throws ClassNotFoundException, SQLException {
		Assertions.assertNotNull(adminService.readPublisher());
	}

	@Test
	void testReadPublisherById() throws ClassNotFoundException, SQLException {
		Assertions.assertNotNull(adminService.readPublisherById(1));
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> adminService.readPublisherById(0));
	}

}
