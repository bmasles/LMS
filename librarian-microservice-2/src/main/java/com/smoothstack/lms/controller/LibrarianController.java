package com.smoothstack.lms.controller;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.smoothstack.lms.model.Book;
import com.smoothstack.lms.model.BookCopy;
import com.smoothstack.lms.model.LibraryBranch;
import com.smoothstack.lms.service.LibrarianService;

@RestController
public class LibrarianController {

	@Autowired
	private LibrarianService librarianService;

	@Autowired
	private DiscoveryClient discoveryClient;

	@RequestMapping("/service-instances/{applicationName}")
	public List<ServiceInstance> serviceInstancesByApplicationName(@PathVariable String applicationName) {
		return this.discoveryClient.getInstances(applicationName);
	}

	@Autowired
	Environment environment;

	@GetMapping(path = "/librarian/port")
	public String port() {
		return environment.getProperty("local.server.port");
	}

	@GetMapping(path = "/librarian/librarybranches")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<LibraryBranch>> getLibraryBranches() {
		try {
			List<LibraryBranch> libraryBranches = librarianService.getLibraryBranches();
			if (libraryBranches.isEmpty()) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(libraryBranches);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping(path = "/librarian/librarybranches/{id}")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<LibraryBranch> getLibraryBranchById(@PathVariable int id) {
		try {
			LibraryBranch libraryBranch = librarianService.getLibraryBranchById(id);
			return ResponseEntity.ok(libraryBranch);
		} catch (EmptyResultDataAccessException e) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PutMapping(path = "/librarian/librarybranches")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> updateLibraryBranch(@RequestBody LibraryBranch libraryBranch) {
		try {
			if (libraryBranch == null || libraryBranch.getId() == null || libraryBranch.getName() == null
					|| libraryBranch.getAddress() == null)
				return ResponseEntity.badRequest().build();
			if (libraryBranch.getName().trim().isEmpty())
				return ResponseEntity.badRequest().build();
			if (libraryBranch.getAddress().trim().isEmpty())
				return ResponseEntity.badRequest().build();
			int rowsAffected = librarianService.updateLibraryBranch(libraryBranch);
			if (rowsAffected == 0) {
				return ResponseEntity.badRequest().build();
			}
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping(path = "/librarian/books")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Book>> getBooks() {
		try {
			List<Book> books = librarianService.getBooks();
			if (books.isEmpty()) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(books);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping(path = "/librarian/bookcopies/books/{bookId}/librarybranches/{libraryBranchId}")
	@Produces({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<BookCopy> getBookCopy(@PathVariable int bookId, @PathVariable int libraryBranchId) {
		try {
			BookCopy bookCopy = librarianService.getBookCopyById(bookId, libraryBranchId);
			return ResponseEntity.ok(bookCopy);
		} catch (EmptyResultDataAccessException e) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PostMapping(path = "/librarian/bookcopies")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> addBookCopy(@RequestBody BookCopy bookCopy) {
		try {
			if (bookCopy == null)
				return ResponseEntity.badRequest().build();
			if (bookCopy.getBook() == null || bookCopy.getBook().getId() == null)
				return ResponseEntity.badRequest().build();
			if (bookCopy.getLibraryBranch() == null || bookCopy.getLibraryBranch().getId() == null)
				return ResponseEntity.badRequest().build();
			if (bookCopy.getAmount() == null || bookCopy.getAmount() < 0)
				return ResponseEntity.badRequest().build();
			librarianService.addBookCopy(bookCopy);
			URI uri = UriComponentsBuilder
					.fromUriString("/librarian/bookcopies/book/{bookId}/librarybranch/{libraryBranchId}")
					.buildAndExpand(bookCopy.getBook().getId(), bookCopy.getLibraryBranch().getId()).toUri();
			return ResponseEntity.created(uri).build();
		} catch (DataIntegrityViolationException e) {
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PutMapping(path = "/librarian/bookcopies")
	@Consumes({ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> updateBookCopy(@RequestBody BookCopy bookCopy) {
		try {
			if (bookCopy == null)
				return ResponseEntity.badRequest().build();
			if (bookCopy.getBook() == null || bookCopy.getBook().getId() == null)
				return ResponseEntity.badRequest().build();
			if (bookCopy.getLibraryBranch() == null || bookCopy.getLibraryBranch().getId() == null)
				return ResponseEntity.badRequest().build();
			if (bookCopy.getAmount() == null || bookCopy.getAmount() < 0)
				return ResponseEntity.badRequest().build();
			int rowsAffected = librarianService.updateBookCopy(bookCopy);
			if (rowsAffected == 0) {
				return ResponseEntity.badRequest().build();
			}
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
