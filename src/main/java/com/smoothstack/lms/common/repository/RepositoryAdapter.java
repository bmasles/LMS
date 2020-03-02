package com.smoothstack.lms.common.repository;

import com.smoothstack.lms.common.model.*;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.PostConstruct;

@Configuration
public class RepositoryAdapter {

	private AuthorCommonRepository authorCommonRepository;
	private BookCommonRepository bookCommonRepository;
	private BorrowerCommonRepository borrowerCommonRepository;
	private BranchCommonRepository branchCommonRepository;
	private CopiesCommonRepository copiesCommonRepository;
	private GenreCommonRepository genreCommonRepository;
	private LoansCommonRepository loansCommonRepository;
	private PublisherCommonRepository publisherCommonRepository;

	private static AuthorCommonRepository authorRepository;
	private static BookCommonRepository bookRepository;
	private static BorrowerCommonRepository borrowerRepository;
	private static BranchCommonRepository branchRepository;
	private static CopiesCommonRepository copiesRepository;
	private static GenreCommonRepository genreRepository;
	private static LoansCommonRepository loansRepository;
	private static PublisherCommonRepository publisherRepository;



	public AuthorCommonRepository getAuthorCommonRepository() {
		return authorCommonRepository;
	}

	@Autowired
	public void setAuthorCommonRepository(AuthorCommonRepository authorCommonRepository) {
		this.authorCommonRepository = authorCommonRepository;
	}

	public BookCommonRepository getBookCommonRepository() {
		return bookCommonRepository;
	}

	@Autowired
	public void setBookCommonRepository(BookCommonRepository bookCommonRepository) {
		this.bookCommonRepository = bookCommonRepository;
	}

	public BorrowerCommonRepository getBorrowerCommonRepository() {
		return borrowerCommonRepository;
	}

	@Autowired
	public void setBorrowerCommonRepository(BorrowerCommonRepository borrowerCommonRepository) {
		this.borrowerCommonRepository = borrowerCommonRepository;
	}

	public BranchCommonRepository getBranchCommonRepository() {
		return branchCommonRepository;
	}

	@Autowired
	public void setBranchCommonRepository(BranchCommonRepository branchCommonRepository) {
		this.branchCommonRepository = branchCommonRepository;
	}

	public CopiesCommonRepository getCopiesCommonRepository() {
		return copiesCommonRepository;
	}

	@Autowired
	public void setCopiesCommonRepository(CopiesCommonRepository copiesCommonRepository) {
		this.copiesCommonRepository = copiesCommonRepository;
	}

	public GenreCommonRepository getGenreCommonRepository() {
		return genreCommonRepository;
	}

	@Autowired
	public void setGenreCommonRepository(GenreCommonRepository genreCommonRepository) {
		this.genreCommonRepository = genreCommonRepository;
	}

	public LoansCommonRepository getLoansCommonRepository() {
		return loansCommonRepository;
	}

	@Autowired
	public void setLoansCommonRepository(LoansCommonRepository loansCommonRepository) {
		this.loansCommonRepository = loansCommonRepository;
	}

	public PublisherCommonRepository getPublisherCommonRepository() {
		return publisherCommonRepository;
	}

	@Autowired
	public void setPublisherCommonRepository(PublisherCommonRepository publisherCommonRepository) {
		this.publisherCommonRepository = publisherCommonRepository;
	}


	public static AuthorCommonRepository getAuthorRepository() {
		return authorRepository;
	}

	public static void setAuthorRepository(AuthorCommonRepository authorRepository) {
		RepositoryAdapter.authorRepository = authorRepository;
	}


	public static BookCommonRepository getBookRepository() {
		return bookRepository;
	}

	public static void setBookRepository(BookCommonRepository bookRepository) {
		RepositoryAdapter.bookRepository = bookRepository;
	}

	public static BorrowerCommonRepository getBorrowerRepository() {
		return borrowerRepository;
	}

	public static void setBorrowerRepository(BorrowerCommonRepository borrowerRepository) {
		RepositoryAdapter.borrowerRepository = borrowerRepository;
	}


	public static BranchCommonRepository getBranchRepository() {
		return branchRepository;
	}

	public static void setBranchRepository(BranchCommonRepository branchRepository) {
		RepositoryAdapter.branchRepository = branchRepository;
	}


	public static CopiesCommonRepository getCopiesRepository() {
		return copiesRepository;
	}

	public static void setCopiesRepository(CopiesCommonRepository copiesRepository) {
		RepositoryAdapter.copiesRepository = copiesRepository;
	}


	public static GenreCommonRepository getGenreRepository() {
		return genreRepository;
	}

	public static void setGenreRepository(GenreCommonRepository genreRepository) {
		RepositoryAdapter.genreRepository = genreRepository;
	}


	public static LoansCommonRepository getLoansRepository() {
		return loansRepository;
	}

	public static void setLoansRepository(LoansCommonRepository loansRepository) {
		RepositoryAdapter.loansRepository = loansRepository;
	}


	public static PublisherCommonRepository getPublisherRepository() {
		return publisherRepository;
	}

	public static void setPublisherRepository(PublisherCommonRepository publisherRepository) {
		RepositoryAdapter.publisherRepository = publisherRepository;
	}

	@PostConstruct
	private void init() {
		setAuthorRepository(this.authorCommonRepository);
		setBookRepository(this.bookCommonRepository);
		setBorrowerRepository(this.borrowerCommonRepository);
		setBranchRepository(this.branchCommonRepository);
		setCopiesRepository(this.copiesCommonRepository);
		setGenreRepository(this.genreCommonRepository);
		setLoansRepository(this.loansCommonRepository);
		setPublisherRepository(this.publisherCommonRepository);
	}

	@SuppressWarnings("unchecked")
	public static <T> JpaRepository<T, Long> getJpaRepository(Class<T> cls) {

		if (cls.equals(Author.class))
			return (JpaRepository<T, Long>) authorRepository;

		if (cls.equals(Book.class))
			return (JpaRepository<T, Long>) bookRepository;

		if (cls.equals(Borrower.class))
			return (JpaRepository<T, Long>) borrowerRepository;

		if (cls.equals(Branch.class))
			return (JpaRepository<T, Long>) branchRepository;

		if (cls.equals(Copies.class))
			return (JpaRepository<T, Long>) copiesRepository;

		if (cls.equals(Genre.class))
			return (JpaRepository<T, Long>) genreRepository;

		if (cls.equals(Loans.class))
			return (JpaRepository<T, Long>) loansRepository;

		if (cls.equals(Publisher.class))
			return (JpaRepository<T, Long>) publisherRepository;

		throw new NoSuchBeanDefinitionException(cls.getSimpleName() + "Repository");

	}

}
