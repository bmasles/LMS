package com.smoothstack.lms.common.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "t_book_loans")
@Access(AccessType.FIELD)
public class Loans implements Serializable {

        @Id
        @Column(name="transactionId")
        @SequenceGenerator(name = "loans", sequenceName = "transactionId", initialValue = 1, allocationSize = 1)
        @GeneratedValue(generator = "loans")
    private long transactionId;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "bookId", referencedColumnName = "bookId")
        @NotNull
    private Book book;


        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "branchId", referencedColumnName = "branchId")
        @NotNull
    private Branch branch;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "borrowerId", referencedColumnName = "borrowerId")
        @NotNull
    private Borrower borrower;

        @Column(name = "loanDateOut")
        @NotNull
    private LocalDate loanDateOut;

        @Column(name = "loanDueDate")
    private LocalDate loanDueDate;

        @Column(name = "loanDateIn")
    private LocalDate loanDateIn;

    public Loans() {
    }

    public Loans(@NotNull Book book, @NotNull Branch branch, @NotNull Borrower borrower, @NotNull LocalDate loanDateOut, LocalDate loanDueDate, LocalDate loanDateIn) {
        this.book = book;
        this.branch = branch;
        this.borrower = borrower;
        this.loanDateOut = loanDateOut;
        this.loanDueDate = loanDueDate;
        this.loanDateIn = loanDateIn;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Borrower getBorrower() {
        return borrower;
    }

    public void setBorrower(Borrower borrower) {
        this.borrower = borrower;
    }

    public LocalDate getLoanDateOut() {
        return loanDateOut;
    }

    public void setLoanDateOut(LocalDate loanDateOut) {
        this.loanDateOut = loanDateOut;
    }

    public void setOut(Date out) {
        this.loanDateOut = out.toLocalDate();
    }

    public LocalDate getLoanDueDate() {
        return loanDueDate;
    }

    public void setLoanDueDate(LocalDate loanDueDate) {
        this.loanDueDate = loanDueDate;
    }

    public void setDue(Date due) {
        this.loanDueDate = due.toLocalDate();
    }

    public LocalDate getLoanDateIn() {
        return loanDateIn;
    }

    public void setLoanDateIn(LocalDate loanDateIn) {
        this.loanDateIn = loanDateIn;
    }

    public void setIn(Date in) {
        this.loanDateIn = in.toLocalDate();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loans loans = (Loans) o;
        return getTransactionId() == loans.getTransactionId() &&
                getBook().equals(loans.getBook()) &&
                getBranch().equals(loans.getBranch()) &&
                getBorrower().equals(loans.getBorrower()) &&
                getLoanDateOut().equals(loans.getLoanDateOut()) &&
                Objects.equals(getLoanDueDate(), loans.getLoanDueDate()) &&
                Objects.equals(getLoanDateIn(), loans.getLoanDateIn());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTransactionId(), getBook().getBookId(), getBranch().getBranchId(), getBorrower().getBorrowerId(), getLoanDateOut(), getLoanDueDate(), getLoanDateIn());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Loans.class.getSimpleName() + "[", "]")
                .add("transactionId=" + transactionId)
                .add("bookId=" + book.getBookId())
                .add("branchId=" + branch.getBranchId())
                .add("borrowerId=" + borrower.getBorrowerId())
                .add("dateOut=" + loanDateOut)
                .add("dueDate=" + loanDueDate)
                .add("dateIn=" + loanDateIn)
                .toString();
    }
}
