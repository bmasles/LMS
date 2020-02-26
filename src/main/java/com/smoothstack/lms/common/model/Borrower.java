package com.smoothstack.lms.common.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "t_borrower")
@Access(AccessType.FIELD)
public class Borrower implements Serializable {

        @Id
        @Column(name = "borrowerId")
        @SequenceGenerator(name = "borrower", sequenceName = "borrowerId", initialValue = 1, allocationSize = 1)
        @GeneratedValue(generator = "borrower")
    private long borrowerId;

        @Column(name = "borrowerName", nullable = false)
        @NotNull
        @NotBlank
    private String borrowerName;

        @Column(name = "borrowerAddress")
    private String borrowerAddress;

        @Column(name = "borrowerPhone")
    private String borrowerPhone;

    public Borrower() {
    }

    public Borrower(@NotNull @NotBlank String borrowerName, String borrowerAddress, String borrowerPhone) {
        this.borrowerName = borrowerName;
        this.borrowerAddress = borrowerAddress;
        this.borrowerPhone = borrowerPhone;
    }

    public long getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(long borrowerId) {
        this.borrowerId = borrowerId;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public String getBorrowerAddress() {
        return borrowerAddress;
    }

    public void setBorrowerAddress(String borrowerAddress) {
        this.borrowerAddress = borrowerAddress;
    }

    public String getBorrowerPhone() {
        return borrowerPhone;
    }

    public void setBorrowerPhone(String borrowerPhone) {
        this.borrowerPhone = borrowerPhone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Borrower borrower = (Borrower) o;
        return getBorrowerId() == borrower.getBorrowerId() &&
                getBorrowerName().equals(borrower.getBorrowerName()) &&
                Objects.equals(getBorrowerAddress(), borrower.getBorrowerAddress()) &&
                Objects.equals(getBorrowerPhone(), borrower.getBorrowerPhone());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getBorrowerId(),
                getBorrowerName(),
                getBorrowerAddress(),
                getBorrowerPhone());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Borrower.class.getSimpleName() + "[", "]")
                .add("id=" + borrowerId)
                .add("name='" + borrowerName + "'")
                .add("address='" + borrowerAddress + "'")
                .add("phone='" + borrowerPhone + "'")
                .toString();
    }
}