package com.smoothstack.lms.borrowermicroservice.depreciated.model;

import com.smoothstack.lms.borrowermicroservice.depreciated.context.annotation.Column;
import com.smoothstack.lms.borrowermicroservice.depreciated.context.annotation.Entity;
import com.smoothstack.lms.borrowermicroservice.depreciated.context.annotation.Id;
import com.smoothstack.lms.borrowermicroservice.depreciated.context.annotation.Table;
@Entity
@Table(name = "tbl_borrower")
public class Borrower {

    @Id
    @Column(name = "cardNo")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    public Borrower() {
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Borrower{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
