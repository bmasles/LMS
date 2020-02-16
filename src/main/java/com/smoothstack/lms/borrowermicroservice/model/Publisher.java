package com.smoothstack.lms.borrowermicroservice.model;


import com.smoothstack.lms.borrowermicroservice.context.annotation.Column;
import com.smoothstack.lms.borrowermicroservice.context.annotation.Entity;
import com.smoothstack.lms.borrowermicroservice.context.annotation.Id;
import com.smoothstack.lms.borrowermicroservice.context.annotation.Table;

@Entity
@Table(name = "tbl_publisher")
public class Publisher /*implements Entity<Publisher>*/ {

    @Id
    @Column(name="publisherId")
    private Integer id;

    @Column(name="publisherName")
    private String name;

    @Column(name="publisherAddress")
    private String address;

    @Column(name="publisherPhone")
    private String phone;

    public Publisher() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
