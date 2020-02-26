package com.smoothstack.lms.common.repository;

import com.smoothstack.lms.common.model.*;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RepositoryAdapter {

    private AuthorCommonRepository authorCommonRepository;
    private BookCommonRepository bookCommonRepository;
    private BorrowerCommonRepository borrowerCommonRepository;
    private BranchCommonRepository branchCommonRepository;
    private CopiesCommonRepository copiesCommonRepository;
    private GenreCommonRepository genreCommonRepository;
    private LoansCommonRepository loansCommonRepository;
    private PublisherCommonRepository publisherCommonRepository;

    private static JpaRepository<Author, Long>  authorRepository;
    private static JpaRepository<Book, Long> bookRepository;
    private static JpaRepository<Borrower, Long>  borrowerRepository;
    private static JpaRepository<Branch, Long>  branchRepository;
    private static JpaRepository<Copies, Long>  copiesRepository;
    private static JpaRepository<Genre, Long>  genreRepository;
    private static JpaRepository<Loans, Long>  loansRepository;
    private static JpaRepository<Publisher, Long>  publisherRepository;

    @Autowired
    private void setAuthorCommonRepository(AuthorCommonRepository authorCommonRepository) {
        this.authorCommonRepository = authorCommonRepository;
    }
    @Autowired
    private void setBookCommonRepository(BookCommonRepository bookCommonRepository) {
        this.bookCommonRepository = bookCommonRepository;
    }
    @Autowired
    private void setBorrowerCommonRepository(BorrowerCommonRepository borrowerCommonRepository) {
        this.borrowerCommonRepository = borrowerCommonRepository;
    }
    @Autowired
    private void setBranchCommonRepository(BranchCommonRepository branchCommonRepository) {
        this.branchCommonRepository = branchCommonRepository;
    }
    @Autowired
    private void setCopiesCommonRepository(CopiesCommonRepository copiesCommonRepository) {
        this.copiesCommonRepository = copiesCommonRepository;
    }
    @Autowired
    private void setGenreCommonRepository(GenreCommonRepository genreCommonRepository) {
        this.genreCommonRepository = genreCommonRepository;
    }
    @Autowired
    private void setLoansCommonRepository(LoansCommonRepository loansCommonRepository) {
        this.loansCommonRepository = loansCommonRepository;
    }
    @Autowired
    private void setPublisherCommonRepository(PublisherCommonRepository publisherCommonRepository) {
        this.publisherCommonRepository = publisherCommonRepository;
    }

    public static JpaRepository<Author, Long> getAuthorRepository() {
        return authorRepository;
    }

    public static void setAuthorRepository(JpaRepository<Author, Long> authorRepository) {
        RepositoryAdapter.authorRepository = authorRepository;
    }

    public static JpaRepository<Book, Long> getBookRepository() {
        return bookRepository;
    }

    public static void setBookRepository(JpaRepository<Book, Long> bookRepository) {
        RepositoryAdapter.bookRepository = bookRepository;
    }

    public static JpaRepository<Borrower, Long> getBorrowerRepository() {
        return borrowerRepository;
    }

    public static void setBorrowerRepository(JpaRepository<Borrower, Long> borrowerRepository) {
        RepositoryAdapter.borrowerRepository = borrowerRepository;
    }

    public static JpaRepository<Branch, Long> getBranchRepository() {
        return branchRepository;
    }

    public static void setBranchRepository(JpaRepository<Branch, Long> branchRepository) {
        RepositoryAdapter.branchRepository = branchRepository;
    }

    public static JpaRepository<Copies, Long> getCopiesRepository() {
        return copiesRepository;
    }

    public static void setCopiesRepository(JpaRepository<Copies, Long> copiesRepository) {
        RepositoryAdapter.copiesRepository = copiesRepository;
    }

    public static JpaRepository<Genre, Long> getGenreRepository() {
        return genreRepository;
    }

    public static void setGenreRepository(JpaRepository<Genre, Long> genreRepository) {
        RepositoryAdapter.genreRepository = genreRepository;
    }

    public static JpaRepository<Loans, Long> getLoansRepository() {
        return loansRepository;
    }

    public static void setLoansRepository(JpaRepository<Loans, Long> loansRepository) {
        RepositoryAdapter.loansRepository = loansRepository;
    }

    public static JpaRepository<Publisher, Long> getPublisherRepository() {
        return publisherRepository;
    }

    public static void setPublisherRepository(JpaRepository<Publisher, Long> publisherRepository) {
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

        throw new NoSuchBeanDefinitionException(cls.getSimpleName()+"Repository");

    }

}
