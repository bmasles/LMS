package com.smoothstack.lms.borrowermicroservice.depreciated.model;


import com.smoothstack.lms.borrowermicroservice.depreciated.context.annotation.*;

import java.util.Objects;

@Entity
@Table(name = "tbl_book")
public class Book {

    @Id
    @Column(name="bookId")
    private Integer id;

    @Column(name="title")
    private String title;

    @ToOne(Publisher.class)
    @JoinColumn(name = "pubId", referencedFieldName = "id", referencedColumnName = "publisherId")
    private Publisher publisher;

    public Book() {

    }

    public Book(String title, Publisher publisher) {
        this.title = title;
        this.publisher = publisher;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(getId(), book.getId()) &&
                getTitle().equals(book.getTitle()) &&
                getPublisher().equals(book.getPublisher());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getPublisher());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Book{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", publisher=").append(publisher);
        sb.append('}');
        return sb.toString();
    }
}
