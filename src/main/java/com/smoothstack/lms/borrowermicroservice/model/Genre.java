package com.smoothstack.lms.borrowermicroservice.model;

import com.smoothstack.lms.borrowermicroservice.context.annotation.Column;
import com.smoothstack.lms.borrowermicroservice.context.annotation.Entity;
import com.smoothstack.lms.borrowermicroservice.context.annotation.Id;
import com.smoothstack.lms.borrowermicroservice.context.annotation.Table;

@Entity
@Table(name = "tbl_genre")
public class Genre /*implements Entity<Genre>*/ {

    @Id
    @Column(name = "genre_id")
    private Integer id;

    @Column(name = "genre_name")
    private String name;
}
