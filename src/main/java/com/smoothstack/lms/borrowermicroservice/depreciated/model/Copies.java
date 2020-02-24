package com.smoothstack.lms.borrowermicroservice.depreciated.model;

import com.smoothstack.lms.borrowermicroservice.depreciated.context.annotation.Column;
import com.smoothstack.lms.borrowermicroservice.depreciated.context.annotation.Entity;
import com.smoothstack.lms.borrowermicroservice.depreciated.context.annotation.Id;
import com.smoothstack.lms.borrowermicroservice.depreciated.context.annotation.Table;

@Entity
@Table(name = "tbl_book_copies")
public class Copies {

    @Id(1)
//    @OneToOne(Book.class)
//    @JoinColumn(name = "bookId", referencedColumnName = "bookId")
    private Book book;

    @Id(2)
//    @OneToOne(Library.class)
//    @JoinColumn(name = "branchId", referencedColumnName = "branchId")
    private Library library;

    @Column(name = "noOfCopies")
    private Integer noOfCopies;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public Integer getNoOfCopies() {
        return noOfCopies;
    }

    public void setNoOfCopies(Integer noOfCopies) {
        this.noOfCopies = noOfCopies;
    }
}
