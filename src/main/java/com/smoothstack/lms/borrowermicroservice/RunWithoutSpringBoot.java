package com.smoothstack.lms.borrowermicroservice;

import com.smoothstack.lms.borrowermicroservice.database.ConnectionFactory;
import com.smoothstack.lms.borrowermicroservice.database.sql.CachedOne;
import com.smoothstack.lms.borrowermicroservice.model.*;
import com.smoothstack.lms.borrowermicroservice.persistance.CrudRepository;
import com.smoothstack.lms.borrowermicroservice.persistance.CrudRepositoryFactory;
import com.smoothstack.lms.borrowermicroservice.service.BorrowerService;

import java.util.List;
import java.util.Optional;

public class RunWithoutSpringBoot {
    public static void main(String[] args) {


        ConnectionFactory connectionFactory = new ConnectionFactory("config.xml");

        BorrowerService borrowerService = new BorrowerService();
        borrowerService.setConnectionFactory(connectionFactory);

        CrudRepository<Book> bookRepository = CrudRepositoryFactory.getRepository(Book.class);
        bookRepository.setConnectionFactory(connectionFactory);

        CrudRepository<Author> authorRepository = CrudRepositoryFactory.getRepository(Author.class);
        authorRepository.setConnectionFactory(connectionFactory);

        CrudRepository<Publisher> publisherRepository = CrudRepositoryFactory.getRepository(Publisher.class);
        publisherRepository.setConnectionFactory(connectionFactory);

        CrudRepository<Loans> loansRepository = CrudRepositoryFactory.getRepository(Loans.class);
        loansRepository.setConnectionFactory(connectionFactory);

        CrudRepository<Library> libraryRepository = CrudRepositoryFactory.getRepository(Library.class);
        libraryRepository.setConnectionFactory(connectionFactory);


        Library l2 = libraryRepository.findByIds(2).orElse(null);

        List<Copies> bookList = borrowerService.getBookListAtLibrary(l2, 5);

        bookList.stream().forEach(System.out::println);


        CachedOne<Book> aBook = bookRepository.cacheOfId(10);


        Publisher p1 = new Publisher("hiO", "ADD", "222");
        publisherRepository.save(p1);
        System.out.println(p1);

        Book b1 = new Book("Title", p1);
        bookRepository.save(b1);
        System.out.println(b1);

        p1.setAddress("Address");
        publisherRepository.save(p1);

        Optional<Loans> oL1 = loansRepository.findByIds(7,2,2);

        System.out.println(oL1);

    }



}
