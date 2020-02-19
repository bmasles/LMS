package com.smoothstack.lms.adminmicroservice.controller;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.smoothstack.lms.adminmicroservice.model.Author;
import com.smoothstack.lms.adminmicroservice.model.Book;
import com.smoothstack.lms.adminmicroservice.model.Borrower;
import com.smoothstack.lms.adminmicroservice.model.Branch;
import com.smoothstack.lms.adminmicroservice.model.Copies;
import com.smoothstack.lms.adminmicroservice.model.Genre;
import com.smoothstack.lms.adminmicroservice.model.Publisher;
import com.smoothstack.lms.adminmicroservice.service.AdminService;

@RestController
public class AdminController {

	@Autowired
	AdminService adminService;

	@GetMapping(path = "/administrator/books")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Book>> getBooks() {
		try {
			List<Book> books = adminService.readBook();
			return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/administrator/books/{id}")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Book> getBook(@PathVariable int id) {
		try {
			Book book = adminService.readBookById(id);
			return new ResponseEntity<Book>(book, HttpStatus.OK);
		} catch (IndexOutOfBoundsException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "administrator/books")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> saveBook(@RequestBody Book book) {
		if (book == null || book.getPublisher().getPublisherId() == null || book.getTitle() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if (book.getAuthors() == null || book.getAuthors().get(0).getAuthorId() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		try {
			adminService.saveBook(book);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(book.getBookId()).toUri();
			return ResponseEntity.created(location).build();
		} catch (SQLException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(path = "administrator/books/{id}")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> updateBook(@RequestBody Book book, @PathVariable int id) {
		if (book == null || book.getPublisher().getPublisherId() == null || book.getTitle() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if (book.getAuthors() == null || book.getAuthors().get(0).getAuthorId() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		book.setBookId(id);
		try {
			adminService.updateBook(book);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(path = "administrator/books/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable int id) {
		try {
			adminService.deleteBook(adminService.readBookById(id));
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (IndexOutOfBoundsException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/administrator/authors")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Author>> getAuthors() {
		try {
			List<Author> authors = adminService.readAuthor();
			return new ResponseEntity<List<Author>>(authors, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/administrator/authors/{id}")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Author> getAuthor(@PathVariable int id) {
		try {
			Author author = adminService.readAuthorById(id);
			return new ResponseEntity<Author>(author, HttpStatus.OK);
		} catch (IndexOutOfBoundsException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "administrator/authors")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> saveAuthor(@RequestBody Author author) {
		if (author == null || author.getAuthorName() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		try {
			adminService.saveAuthor(author);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(author.getAuthorId()).toUri();
			return ResponseEntity.created(location).build();
		} catch (SQLException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(path = "administrator/authors/{id}")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> updateAuthor(@RequestBody Author author, @PathVariable int id) {
		if (author == null || author.getAuthorName() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		author.setAuthorId(id);
		try {
			adminService.updateAuthor(author);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(path = "administrator/authors/{id}")
	public ResponseEntity<Void> deleteAuthor(@PathVariable int id) {
		try {
			adminService.deleteAuthor(adminService.readAuthorById(id));
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (IndexOutOfBoundsException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/administrator/genres")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Genre>> getGenres() {
		try {
			List<Genre> genres = adminService.readGenre();
			return new ResponseEntity<List<Genre>>(genres, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/administrator/genres/{id}")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Genre> getGenre(@PathVariable int id) {
		try {
			Genre genre = adminService.readGenreById(id);
			return new ResponseEntity<Genre>(genre, HttpStatus.OK);
		} catch (IndexOutOfBoundsException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "administrator/genres")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> saveGenre(@RequestBody Genre genre) {
		if (genre == null || genre.getGenreName() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		try {
			adminService.saveGenre(genre);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(genre.getGenreId()).toUri();
			return ResponseEntity.created(location).build();
		} catch (SQLException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(path = "administrator/genres/{id}")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> updateGenre(@RequestBody Genre genre, @PathVariable int id) {
		if (genre == null || genre.getGenreName() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		genre.setGenreId(id);
		try {
			adminService.updateGenre(genre);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(path = "administrator/genres/{id}")
	public ResponseEntity<Void> deleteGenre(@PathVariable int id) {
		try {
			adminService.deleteGenre(adminService.readGenreById(id));
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (IndexOutOfBoundsException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/administrator/borrowers")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Borrower>> getBorrowers() {
		try {
			List<Borrower> borrowers = adminService.readBorrower();
			return new ResponseEntity<List<Borrower>>(borrowers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/administrator/borrowers/{id}")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Borrower> getBorrower(@PathVariable int id) {
		try {
			Borrower borrower = adminService.readBorrowerById(id);
			return new ResponseEntity<Borrower>(borrower, HttpStatus.OK);
		} catch (IndexOutOfBoundsException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "administrator/borrowers")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> saveBorrower(@RequestBody Borrower borrower) {
		if (borrower == null || borrower.getAddress() == null || borrower.getName() == null
				|| borrower.getPhone() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		try {
			adminService.saveBorrower(borrower);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(borrower.getCardNo()).toUri();
			return ResponseEntity.created(location).build();
		} catch (SQLException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(path = "administrator/borrowers/{id}")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> updateBorrower(@RequestBody Borrower borrower, @PathVariable int id) {
		if (borrower == null || borrower.getAddress() == null || borrower.getName() == null
				|| borrower.getPhone() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		borrower.setCardNo(id);
		try {
			adminService.updateBorrower(borrower);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(path = "administrator/borrowers/{id}")
	public ResponseEntity<Void> deleteBorrower(@PathVariable int id) {
		try {
			adminService.deleteBorrower(adminService.readBorrowerById(id));
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (IndexOutOfBoundsException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/administrator/branches")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Branch>> getBranchs() {
		try {
			List<Branch> branchs = adminService.readBranch();
			return new ResponseEntity<List<Branch>>(branchs, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/administrator/branches/{id}")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Branch> getBranch(@PathVariable int id) {
		try {
			Branch branch = adminService.readBranchById(id);
			return new ResponseEntity<Branch>(branch, HttpStatus.OK);
		} catch (IndexOutOfBoundsException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "administrator/branches")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> saveBranch(@RequestBody Branch branch) {
		if (branch == null || branch.getBranchName() == null || branch.getBranchAddress() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		try {
			adminService.saveBranch(branch);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(branch.getBranchId()).toUri();
			return ResponseEntity.created(location).build();
		} catch (SQLException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(path = "administrator/branches/{id}")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> updateBranch(@RequestBody Branch branch, @PathVariable int id) {
		if (branch == null || branch.getBranchName() == null || branch.getBranchAddress() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		branch.setBranchId(id);
		try {
			adminService.updateBranch(branch);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(path = "administrator/branches/{id}")
	public ResponseEntity<Void> deleteBranch(@PathVariable int id) {
		try {
			adminService.deleteBranch(adminService.readBranchById(id));
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (IndexOutOfBoundsException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/administrator/copies")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Copies>> getCopies() {
		try {
			List<Copies> copiess = adminService.readCopies();
			return new ResponseEntity<List<Copies>>(copiess, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/administrator/copies/{branchId}/{bookId}")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Copies> getCopy(@PathVariable int branchId, @PathVariable int bookId) {
		try {
			Copies copies = adminService.readCopyById(branchId, bookId);
			return new ResponseEntity<Copies>(copies, HttpStatus.OK);
		} catch (IndexOutOfBoundsException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "administrator/copies")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> saveCopies(@RequestBody Copies copies) {
		if (copies.getBookId() == null || copies.getBranchId() == null || copies.getNoOfCopies() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		try {
			adminService.saveCopies(copies);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{/branchId}/{bookId}")
					.buildAndExpand(copies.getBranchId(), copies.getBookId()).toUri();
			return ResponseEntity.created(location).build();
		} catch (SQLException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(path = "administrator/copies/{branchId}/{bookId}")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> updateCopies(@RequestBody Copies copies, @PathVariable int branchId,
			@PathVariable int bookId) {
		if (copies.getNoOfCopies() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		copies.setBranchId(branchId);
		copies.setBookId(bookId);
		try {
			adminService.updateCopies(copies);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(path = "administrator/copies/{brancId}/{bookId}")
	public ResponseEntity<Void> deleteCopies(@PathVariable int branchId, @PathVariable int bookId) {
		try {
			adminService.deleteCopies(adminService.readCopyById(branchId, bookId));
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (IndexOutOfBoundsException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/administrator/publishers")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Publisher>> getPublishers() {
		try {
			List<Publisher> publishers = adminService.readPublisher();
			return new ResponseEntity<List<Publisher>>(publishers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/administrator/publishers/{id}")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Publisher> getPublisher(@PathVariable int id) {
		try {
			Publisher publisher = adminService.readPublisherById(id);
			return new ResponseEntity<Publisher>(publisher, HttpStatus.OK);
		} catch (IndexOutOfBoundsException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "administrator/publishers")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> savePublisher(@RequestBody Publisher publisher) {
		if (publisher == null || publisher.getPublisherName() == null || publisher.getPublisherAddress() == null
				|| publisher.getPublisherPhone() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		try {
			adminService.savePublisher(publisher);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(publisher.getPublisherId()).toUri();
			return ResponseEntity.created(location).build();
		} catch (SQLException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(path = "administrator/publishers/{id}")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> updatePublisher(@RequestBody Publisher publisher, @PathVariable int id) {
		if (publisher == null || publisher.getPublisherName() == null || publisher.getPublisherAddress() == null
				|| publisher.getPublisherPhone() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		publisher.setPublisherId(id);
		try {
			adminService.updatePublisher(publisher);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(path = "administrator/publishers/{id}")
	public ResponseEntity<Void> deletePublisher(@PathVariable int id) {
		try {
			adminService.deletePublisher(adminService.readPublisherById(id));
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (IndexOutOfBoundsException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}