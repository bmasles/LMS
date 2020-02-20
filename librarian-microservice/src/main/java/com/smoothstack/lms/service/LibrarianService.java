package com.smoothstack.lms.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.smoothstack.lms.dao.BookCopyDao;
import com.smoothstack.lms.dao.BookDao;
import com.smoothstack.lms.dao.LibraryBranchDao;
import com.smoothstack.lms.model.Book;
import com.smoothstack.lms.model.BookCopy;
import com.smoothstack.lms.model.LibraryBranch;

@Service
public class LibrarianService {

	@Autowired
	private PlatformTransactionManager platformTransactionManager;

	@Autowired
	private LibraryBranchDao libraryBranchDao;

	@Autowired
	private BookDao bookDao;

	@Autowired
	private BookCopyDao bookCopyDao;

	public List<LibraryBranch> getLibraryBranches() throws SQLException {
		return libraryBranchDao.read();
	}

	public LibraryBranch getLibraryBranchById(int id) throws SQLException {
		return libraryBranchDao.readOne(id);
	}

	public int updateLibraryBranch(LibraryBranch libraryBranch) throws SQLException {
		DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
		TransactionStatus status = platformTransactionManager.getTransaction(defaultTransactionDefinition);
		try {
			int rowsAffected = libraryBranchDao.update(libraryBranch);
			platformTransactionManager.commit(status);
			return rowsAffected;
		} catch (Exception e) {
			platformTransactionManager.rollback(status);
			throw e;
		}
	}

	public List<Book> getBooks() throws SQLException {
		return bookDao.read();
	}

	public BookCopy getBookCopyById(int bookId, int libraryBranchId) throws SQLException {
		return bookCopyDao.readOne(bookId, libraryBranchId);
	}

	public void addBookCopy(BookCopy bookCopy) throws SQLException {
		DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
		TransactionStatus status = platformTransactionManager.getTransaction(defaultTransactionDefinition);
		try {
			bookCopyDao.create(bookCopy);
			platformTransactionManager.commit(status);
		} catch (Exception e) {
			platformTransactionManager.rollback(status);
			throw e;
		}
	}

	public int updateBookCopy(BookCopy bookCopy) throws SQLException {
		DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
		TransactionStatus status = platformTransactionManager.getTransaction(defaultTransactionDefinition);
		try {
			int rowsAffected = bookCopyDao.update(bookCopy);
			platformTransactionManager.commit(status);
			return rowsAffected;
		} catch (Exception e) {
			platformTransactionManager.rollback(status);
			throw e;
		}
	}

}