package com.smoothstack.lms.borrowermicroservice.model;

import com.smoothstack.lms.borrowermicroservice.context.annotation.Column;
import com.smoothstack.lms.borrowermicroservice.context.annotation.Entity;
import com.smoothstack.lms.borrowermicroservice.context.annotation.Table;

import java.time.LocalDate;

@Entity
@Table(name = "tbl_book_loans")
public class Loans /*implements Entity<Loans>*/ {

//    @OneToOne(Book.class)
//    @JoinColumn(name = "bookId", referencedColumnName = "bookId")
    private Book book;

//    @OneToOne(Library.class)
//    @JoinColumn(name = "branchId", referencedColumnName = "branchId")
    private Library library;

//    @OneToOne(Borrower.class)
//    @JoinColumn(name = "cardNo", referencedColumnName = "cardNo")
    private Borrower borrower;

    @Column(name = "dateOut")
    private LocalDate out;

    @Column(name = "dueDate")
    private LocalDate due;

    @Column(name = "dateIn")
    private LocalDate in;

    public Loans() {
    }

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

    public Borrower getBorrower() {
        return borrower;
    }

    public void setBorrower(Borrower borrower) {
        this.borrower = borrower;
    }

    public LocalDate getOut() {
        return out;
    }

    public void setOut(LocalDate out) {
        this.out = out;
    }

    public LocalDate getDue() {
        return due;
    }

    public void setDue(LocalDate due) {
        this.due = due;
    }

    public LocalDate getIn() {
        return in;
    }

    public void setIn(LocalDate in) {
        this.in = in;
    }
}
