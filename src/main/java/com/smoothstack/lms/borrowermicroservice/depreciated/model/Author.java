package com.smoothstack.lms.borrowermicroservice.depreciated.model;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tbl_author")
@Access(AccessType.FIELD)
public class Author  {
//sd
        @Id
        @Column(name="authorId")
        @SequenceGenerator(name = "author", sequenceName = "authorId", initialValue = 1, allocationSize = 1)
        @GeneratedValue(generator = "author")
    private long id;

        @Column(name="authorName")
    private String name;

        @ManyToMany(mappedBy = "authorCollection")
        @JoinColumn(name = "bookId", referencedColumnName = "bookId")
    private Set<Book> bookCollection;

    public Author() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Book> getBookCollection() {
        return bookCollection;
    }

    public void setBookCollection(Set<Book> bookCollection) {
        this.bookCollection = bookCollection;
    }
}