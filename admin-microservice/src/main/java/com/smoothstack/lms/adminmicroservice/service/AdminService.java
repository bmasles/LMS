package com.smoothstack.lms.adminmicroservice.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smoothstack.lms.adminmicroservice.dao.AuthorDAO;
import com.smoothstack.lms.adminmicroservice.dao.BookDAO;
import com.smoothstack.lms.adminmicroservice.dao.BorrowerDAO;
import com.smoothstack.lms.adminmicroservice.dao.BranchDAO;
import com.smoothstack.lms.adminmicroservice.dao.CopiesDAO;
import com.smoothstack.lms.adminmicroservice.dao.GenreDAO;
import com.smoothstack.lms.adminmicroservice.dao.PublisherDAO;
import com.smoothstack.lms.adminmicroservice.model.Author;
import com.smoothstack.lms.adminmicroservice.model.Book;
import com.smoothstack.lms.adminmicroservice.model.Borrower;
import com.smoothstack.lms.adminmicroservice.model.Branch;
import com.smoothstack.lms.adminmicroservice.model.Copies;
import com.smoothstack.lms.adminmicroservice.model.Genre;
import com.smoothstack.lms.adminmicroservice.model.Publisher;

@Service
public class AdminService {
	
	@Autowired
	AuthorDAO authorDao;
	
	@Autowired
	BookDAO bookDao;
	
	@Autowired
	GenreDAO genreDao;
	
	@Autowired
	BorrowerDAO borrowerDao;
	
	@Autowired
	BranchDAO branchDao;
	
	@Autowired
	CopiesDAO copiesDao;
	
	@Autowired
	PublisherDAO publisherDao;
	

	public void saveAuthor(Author auth) throws ClassNotFoundException, SQLException {
		try {
			auth.setAuthorId(authorDao.addAuthor(auth));
			for (Book bk : auth.getBooks())
				authorDao.insertBookAuthors(auth, bk);
			authorDao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not save author");
			throw e;
		}
	}

	public void updateAuthor(Author auth) throws ClassNotFoundException, SQLException {
		try {
			authorDao.updateAuthor(auth);
			authorDao.deleteBookAuthors(auth);
			for (Book bk : auth.getBooks())
				authorDao.insertBookAuthors(auth, bk);
			authorDao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not update author");
			throw e;
		}
	}

	public void deleteAuthor(Author auth) throws ClassNotFoundException, SQLException {
		try {
			authorDao.deleteAuthor(auth);
			authorDao.deleteBookAuthors(auth);
			for (Book book : auth.getBooks())
				bookDao.deleteBook(book);
			bookDao.commit();
			authorDao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not delete author");
			throw e;
		}
	}

	public List<Author> readAuthor() throws ClassNotFoundException, SQLException {
		try {
			return authorDao.readAuthors();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read author");
			throw e;
		}
	}

	public Author readAuthorById(Integer authorId) throws ClassNotFoundException, SQLException {
		try {
			return authorDao.readAuthorById(authorId);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read author");
			e.printStackTrace();
			throw e;
		}
	}

	public void saveBook(Book book) throws ClassNotFoundException, SQLException {
		try {
			book.setBookId(bookDao.addBook(book));
			for (Author aut : book.getAuthors())
				bookDao.insertBookAuthors(aut, book);
			for (Genre gen : book.getGenres())
				bookDao.insertBookGenres(gen, book);
			bookDao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not save book");
			throw e;
		}
	}

	public void updateBook(Book book) throws ClassNotFoundException, SQLException {
		try {
			bookDao.updateBook(book);
			bookDao.deleteBookAuthors(book);
			bookDao.deleteBookGenres(book);
			for (Author auth : book.getAuthors())
				bookDao.insertBookAuthors(auth, book);
			for (Genre gen : book.getGenres())
				bookDao.insertBookGenres(gen, book);
			bookDao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not update book");
			throw e;
		}
	}

	public void deleteBook(Book book) throws ClassNotFoundException, SQLException {
		try {
			bookDao.deleteBook(book);
			bookDao.deleteBookAuthors(book);
			bookDao.deleteBookGenres(book);
			copiesDao.deleteCopiesByBookId(book.getBookId());
			bookDao.commit();
			copiesDao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not delete book");
			throw e;
		}
	}

	public List<Book> readBook() throws ClassNotFoundException, SQLException {
		try {
			return bookDao.readBooks();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read book");
			throw e;
		}
	}

	public Book readBookById(Integer bookId) throws ClassNotFoundException, SQLException {
		try {
			return bookDao.readBookById(bookId);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read book");
			e.printStackTrace();
			throw e;
		}
	}

	public void saveGenre(Genre gen) throws ClassNotFoundException, SQLException {
		try {
			gen.setGenreId(genreDao.addGenre(gen));
			for (Book bk : gen.getBooks())
				genreDao.insertBookGenres(gen, bk);
			genreDao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not create genre");
			throw e;
		}
	}

	public void updateGenre(Genre gen) throws ClassNotFoundException, SQLException {
		try {
			genreDao.updateGenre(gen);
			genreDao.deleteBookGenres(gen);
			for (Book book : gen.getBooks())
				genreDao.insertBookGenres(gen, book);
			genreDao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not update genre");
			throw e;
		}
	}

	public void deleteGenre(Genre gen) throws ClassNotFoundException, SQLException {
		try {
			genreDao.deleteGenre(gen);
			genreDao.deleteBookGenres(gen);
			genreDao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not delete genre");
			throw e;
		}
	}

	public List<Genre> readGenre() throws ClassNotFoundException, SQLException {
		try {
			return genreDao.readGenre();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read genre");
			throw e;
		}
	}

	public Genre readGenreById(Integer genreId) throws ClassNotFoundException, SQLException {
		try {
			return genreDao.readGenreById(genreId);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read genre");
			e.printStackTrace();
			throw e;
		}
	}

	public void saveBorrower(Borrower bor) throws ClassNotFoundException, SQLException {
		try {
			bor.setCardNo(borrowerDao.addBorrower(bor));
			borrowerDao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not save borrower");
			throw e;
		}
	}

	public void updateBorrower(Borrower bor) throws ClassNotFoundException, SQLException {
		try {
			borrowerDao.updateBorrower(bor);
			borrowerDao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not update borrower");
			throw e;
		}
	}

	public void deleteBorrower(Borrower bor) throws ClassNotFoundException, SQLException {
		try {
			borrowerDao.deleteBorrower(bor);
			borrowerDao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not delete borrower");
			throw e;
		}
	}

	public List<Borrower> readBorrower() throws ClassNotFoundException, SQLException {
		try {
			return borrowerDao.readBorrower();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read borrower");
			throw e;
		}
	}

	public Borrower readBorrowerById(Integer cardNo) throws ClassNotFoundException, SQLException {
		try {
			return borrowerDao.readBorrowerById(cardNo);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read borrower");
			e.printStackTrace();
			throw e;
		}
	}

	public void saveBranch(Branch brch) throws ClassNotFoundException, SQLException {
		try {
			brch.setBranchId(branchDao.addBranch(brch));
			branchDao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not create branch");
			throw e;
		}
	}

	public void updateBranch(Branch brch) throws ClassNotFoundException, SQLException {
		try {
			branchDao.updateBranch(brch);
			branchDao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not update branch");
			throw e;
		}
	}

	public void deleteBranch(Branch brch) throws ClassNotFoundException, SQLException {
		try {
			branchDao.deleteBranch(brch);
			branchDao.deleteBranchCopies(brch);
			branchDao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not delete branch");
			throw e;
		}
	}

	public List<Branch> readBranch() throws ClassNotFoundException, SQLException {
		try {
			return branchDao.readBranch();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read branch");
			throw e;
		}
	}

	public Branch readBranchById(Integer branchId) throws ClassNotFoundException, SQLException {
		try {
			return branchDao.readBranchById(branchId);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read branch");
			e.printStackTrace();
			throw e;
		}
	}

	public void saveCopies(Copies copy) throws ClassNotFoundException, SQLException {
		try {
			copiesDao.addCopies(copy);
			copiesDao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Could not create copies");
			throw e;
		}
	}

	public void updateCopies(Copies copy) throws ClassNotFoundException, SQLException {
		try {
			copiesDao.updateCopies(copy);
			copiesDao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not update copies");
			throw e;
		}
	}

	public void deleteCopies(Copies copy) throws ClassNotFoundException, SQLException {
		try {
			copiesDao.deleteCopies(copy);
			copiesDao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not delete copies");
			throw e;
		}
	}

	public List<Copies> readCopies() throws ClassNotFoundException, SQLException {
		try {
			return copiesDao.readCopies();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read copies");
			throw e;
		}
	}

	public List<Copies> readCopiesById(Integer branchId) throws ClassNotFoundException, SQLException {
		try {
			return copiesDao.readCopyById(branchId);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read copies");
			throw e;
		}
	}

	public Copies readCopyById(Integer branchId, Integer bookId) throws ClassNotFoundException, SQLException {
		try {
			return copiesDao.readCopyById(branchId, bookId).get(0);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read copies");
			throw e;
		}
	}

	public void savePublisher(Publisher pub) throws ClassNotFoundException, SQLException {
		try {
			pub.setPublisherId(publisherDao.addPublisher(pub));
			publisherDao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not creat publisher");
			throw e;
		}
	}

	public void updatePublisher(Publisher pub) throws ClassNotFoundException, SQLException {
		try {
			publisherDao.updatePublisher(pub);
			publisherDao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not update publisher");
			throw e;
		}
	}

	public void deletePublisher(Publisher pub) throws ClassNotFoundException, SQLException {
		try {
			publisherDao.deletePublisher(pub);
			publisherDao.deletePublisherBooks(pub);
			publisherDao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not delete publisher");
			throw e;
		}
	}

	public List<Publisher> readPublisher() throws ClassNotFoundException, SQLException {
		try {
			PublisherDAO publisherDao = new PublisherDAO();
			return publisherDao.readPublisher();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read publisher");
			throw e;
		}
	}

	public Publisher readPublisherById(Integer publisherId) throws ClassNotFoundException, SQLException {
		try {
			return publisherDao.readPublisherById(publisherId);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read publisher");
			e.printStackTrace();
			throw e;
		}
	}

}
