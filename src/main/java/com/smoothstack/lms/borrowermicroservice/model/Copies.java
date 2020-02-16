package com.smoothstack.lms.borrowermicroservice.model;

import com.smoothstack.lms.borrowermicroservice.context.annotation.Column;
import com.smoothstack.lms.borrowermicroservice.context.annotation.Entity;
import com.smoothstack.lms.borrowermicroservice.context.annotation.Table;

@Entity
@Table(name = "tbl_book_copies")
public class Copies {

//    @OneToOne(Book.class)
//    @JoinColumn(name = "bookId", referencedColumnName = "bookId")
    private Book book;

//    @OneToOne(Library.class)
//    @JoinColumn(name = "branchId", referencedColumnName = "branchId")
    private Library library;

    @Column(name = "noOfCopies")
    private Integer noOfCopies;
}
