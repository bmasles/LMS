package com.smoothstack.lms.borrowermicroservice.model;


import com.smoothstack.lms.borrowermicroservice.context.annotation.*;
import com.smoothstack.lms.borrowermicroservice.database.sql.RelationToOne;

@Entity
@Table(name = "tbl_book")
public class Book {

    @Id
    @Column(name="bookId")
    private Integer id;

    @Column(name="title")
    private String title;

    @OneToOne(Publisher.class)
    @JoinColumn(name = "pubId", referencedColumnName = "publisherId")
    private RelationToOne<Publisher> publisher;

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
        return publisher.get().orElse(null);
    }

    public void setPublisher(Publisher publisher) {
        this.publisher.set(publisher);
    }
}
