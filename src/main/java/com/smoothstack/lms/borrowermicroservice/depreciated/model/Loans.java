package com.smoothstack.lms.borrowermicroservice.depreciated.model;

import com.smoothstack.lms.borrowermicroservice.depreciated.context.annotation.*;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "tbl_book_loans")
public class Loans /*implements Entity<Loans>*/ {

    @Id(1)
    @ToOne(Book.class)
    @JoinColumn(name = "bookId", referencedFieldName = "id", referencedColumnName = "bookId")
    private Book book;

    @Id(2)
    @ToOne(Library.class)
    @JoinColumn(name = "branchId", referencedFieldName = "id", referencedColumnName = "branchId")
    private Library library;

    @Id(3)
    @ToOne(Borrower.class)
    @JoinColumn(name = "cardNo", referencedFieldName = "id", referencedColumnName = "cardNo")
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

    public void setOut(Date out) {
        this.out = out==null?null:out.toLocalDate();
    }

    public LocalDate getDue() {
        return due;
    }

    public void setDue(LocalDate due) {
        this.due = due;
    }

    public void setDue(Date due) {
        this.due = due==null?null:due.toLocalDate();
    }

    public LocalDate getIn() {
        return in;
    }

    public void setIn(LocalDate in) {
        this.in = in;
    }

    public void setIn(Date in) {
        this.in = in==null?null:in.toLocalDate();
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Loans{");
        sb.append("book=").append(getBook());
        sb.append(", library=").append(getLibrary());
        sb.append(", borrower=").append(getBorrower());
        sb.append(", out=").append(out);
        sb.append(", due=").append(due);
        sb.append(", in=").append(in);
        sb.append('}');
        return sb.toString();
    }
}
