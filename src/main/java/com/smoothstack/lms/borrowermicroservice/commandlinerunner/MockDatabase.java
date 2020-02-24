package com.smoothstack.lms.borrowermicroservice.commandlinerunner;

import au.com.bytecode.opencsv.CSVReader;
import com.smoothstack.lms.borrowermicroservice.Debug;
import com.smoothstack.lms.borrowermicroservice.common.model.*;
import com.smoothstack.lms.borrowermicroservice.common.repository.BookRepository;
import com.smoothstack.lms.borrowermicroservice.common.repository.BorrowerRepository;
import com.smoothstack.lms.borrowermicroservice.common.repository.CopiesRepository;
import com.smoothstack.lms.borrowermicroservice.common.repository.LibraryBranchRepository;
import com.smoothstack.lms.borrowermicroservice.service.BookServices;
import com.smoothstack.lms.borrowermicroservice.service.BorrowerService;
import com.smoothstack.lms.borrowermicroservice.service.LibraryBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Configuration
public class MockDatabase implements CommandLineRunner {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BorrowerRepository borrowerRepository;

    @Autowired
    LibraryBranchRepository libraryBranchRepository;

    @Autowired
    CopiesRepository copiesRepository;

    @Autowired
    BookServices bookServices;

    @Autowired
    BorrowerService borrowerService;

    @Autowired
    LibraryBranchService libraryBranchService;

    @Override
    public void run(String... args) throws Exception {
        mockLibrary();
        mockBook();
        mockBorrower();
        //mockCopiesAndCheckout();

        Debug.println("**** DONE *****");

    }

    public void mockCopiesAndCheckout() {
        Random r = new Random();
        long bookCount = bookRepository.count();

        long libraryCount = libraryBranchRepository.count();

        long borrowerCount = borrowerRepository.count();



        for (long bookId = 1; bookId <= bookCount; bookId++) {
            Optional<Book> book = bookRepository.findById(bookId);

            for (long branchId = 0; branchId < libraryCount; branchId++) {
                Optional<LibraryBranch> libraryBranch = libraryBranchRepository.findById(branchId);

                if (book.isPresent()&&libraryBranch.isPresent()) {

                    Copies copies = new Copies(book.get(), libraryBranch.get(), r.nextInt(15));

                    copiesRepository.save(copies);

                    Optional<Borrower> borrower = borrowerRepository
                            .findById(1 + (long) r.nextInt(-1 + (int) borrowerCount));

                    borrower.ifPresent(b -> borrowerService.checkoutBookFromLibraryByBorrower(
                            b, libraryBranch.get(), book.get()
                    ));
                }
            }

        }

    }

    public void mockBook() throws IOException {


        CSVReader reader = new CSVReader(new FileReader("data/raw/book.csv"), ',' , '"' , 2);

        List<String[]> allRows = reader.readAll();

        //Read CSV line by line and use the string array as you want
        for(String[] row : allRows){
            if (row[0] == null || row[1] == null || row[2] == null || row[4] == null)
                continue;

            if (row[0].trim().isEmpty() ||
                row[1].trim().isEmpty() ||
                row[2].trim().isEmpty() ||
                row[4].trim().isEmpty()
            )
                continue;


            System.out.printf("Processing %s\n", Arrays.toString(row));
            Publisher publisher = new Publisher(row[4], "1234 "+row[4]+" Boulevard, Fairfax, VA 22033", "(555) 231-1234");
            Author author = new Author(row[1]);
            Book book = new Book(row[0]);
            Genre genre = new Genre(row[2]);

            if (bookRepository.countAllByBookTitleIgnoreCase(book.getBookTitle()) > 0)
                    continue;


            bookServices.buildAndSave(book, publisher, author, genre);

        }
    }

    public void mockBorrower() {
        List<String> people = randomName();

        people.stream()
                .filter(s->!borrowerRepository.existsByBorrowerNameIgnoreCase(s))
                .map(s->new Borrower(s, "123 "+s.split(" ")[1]+" Way, Fairfax, VA 22033", "(555) 231-4321"))
                .forEach(borrowerService::buildAndSave);
    }

    public void mockLibrary() {
        List<String> town = randomTown();

        town.stream()
                .filter(t ->!libraryBranchRepository.existsByBranchNameIgnoreCase(t))
                .map(t->new LibraryBranch(t,"12 "+t+" Road, "+t+" City, VA 20200"))
                .forEach(libraryBranchService::buildAndSave);

    }

    public List<String> randomTown() {
        return new ArrayList<>(Arrays.asList(
                "Brookmere",
                "Beechwick",
                "Violetlight",
                "Woodcoast",
                "Southcoast"));
    }

    public List<String> randomName() {
        return new ArrayList<>(
                Arrays.asList(
                        "Somer Simkins",
                        "Kristal Kleinschmidt",
                        "Pandora Portalatin",
                        "Shantelle Sugg",
                        "Jaquelyn Johanson",
                        "Desiree Dilday",
                        "Zena Zachery",
                        "Travis Tillson",
                        "Luanna Leopold",
                        "Carolina Clear",
                        "Hollis Hoffmeister",
                        "Krysten Kin",
                        "Sanford Scarpa",
                        "Alina Aldinger",
                        "Ilana Ingerson",
                        "Ardell Alcantara",
                        "Nickole Negley",
                        "Tawanna Tarbell",
                        "Berta Borger",
                        "Corene Cassette",
                        "Ronda Rizo",
                        "Burl Buxton",
                        "Darren Dobson",
                        "Jacki Jaime",
                        "Casimira Calahan",
                        "Deanne Daves",
                        "Page Porcaro",
                        "Dinah Ducksworth",
                        "Carie Challis",
                        "Danita Delahanty",
                        "Arthur Alcala",
                        "Annette Alcon",
                        "Cindy Corella",
                        "Caroyln Canino",
                        "Laurel Lehmann",
                        "Neely Newbern",
                        "Lakeesha Leto",
                        "Israel Ivie",
                        "Clarence Castrejon",
                        "Kellie Kahan",
                        "Kimbery Klopfenstein",
                        "Sook Sowers",
                        "Brain Bal",
                        "Ruthie Randall",
                        "Dick Duclos",
                        "Laurice Lebow",
                        "Milton Mirando",
                        "Edris Elkins",
                        "Latarsha Lovato",
                        "Lawrence Lalli"));
    }



}
