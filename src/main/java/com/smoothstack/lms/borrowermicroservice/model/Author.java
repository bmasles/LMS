package com.smoothstack.lms.borrowermicroservice.model;

import com.smoothstack.lms.borrowermicroservice.context.annotation.Column;
import com.smoothstack.lms.borrowermicroservice.context.annotation.Entity;
import com.smoothstack.lms.borrowermicroservice.context.annotation.Id;
import com.smoothstack.lms.borrowermicroservice.context.annotation.Table;
import com.smoothstack.lms.borrowermicroservice.persistance.CrudRepository;

@Entity
@Table(name = "tbl_author")
public class Author  {

    @Id
    @Column(name="authorId")
    private Integer id;

    @Column(name="authorName")
    private String name;

    public Author() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}