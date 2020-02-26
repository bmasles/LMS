package com.smoothstack.lms.common.model;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Entity
@Table(name = "t_publisher")
@Access(AccessType.FIELD)
public class Publisher implements Serializable {

        @Id
        @Column(name = "publisherId")
        @SequenceGenerator(name = "publisher", sequenceName = "publisherId", initialValue = 1, allocationSize = 1)
        @GeneratedValue(generator = "publisher")
    private long publisherId;

        @Column(name = "publisherName", nullable = false)
        @NotNull
        @NotBlank
    private String publisherName;

        @Column(name = "publisherAddress")
    private String publisherAddress;

        @Column(name = "publisherPhone")
    private String publisherPhone;

        @OneToMany(mappedBy = "publisher")
    private Set<Book> publisherBookSet = new HashSet<>();

    public Publisher() {
    }

    public Publisher(@NotNull @NotBlank String publisherName, String publisherAddress, String publisherPhone) {
        this.publisherName = publisherName;
        this.publisherAddress = publisherAddress;
        this.publisherPhone = publisherPhone;
    }

    public long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(long publisherId) {
        this.publisherId = publisherId;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getPublisherAddress() {
        return publisherAddress;
    }

    public void setPublisherAddress(String publisherAddress) {
        this.publisherAddress = publisherAddress;
    }

    public String getPublisherPhone() {
        return publisherPhone;
    }

    public void setPublisherPhone(String publisherPhone) {
        this.publisherPhone = publisherPhone;
    }

    public Set<Book> getPublisherBookSet() {
        return publisherBookSet;
    }

    public void setPublisherBookSet(Set<Book> publisherBookSet) {
        this.publisherBookSet = publisherBookSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publisher publisher = (Publisher) o;
        return getPublisherId() == publisher.getPublisherId() &&
                getPublisherName().equals(publisher.getPublisherName()) &&
                Objects.equals(getPublisherAddress(), publisher.getPublisherAddress()) &&
                Objects.equals(getPublisherPhone(), publisher.getPublisherPhone()) &&
                Objects.equals(getPublisherBookSet(), publisher.getPublisherBookSet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getPublisherId(),
                getPublisherName(),
                getPublisherAddress(),
                getPublisherPhone());
    }

    @Override
    public String toString() {

        return new StringJoiner(", ", Publisher.class.getSimpleName() + "[", "]")
                .add("id=" + publisherId)
                .add("name='" + publisherName + "'")
                .add("address='" + publisherAddress + "'")
                .add("phone='" + publisherPhone + "'")
                .add("bookSet=" + publisherBookSet.stream()
                        .map(book -> Long.toString(book.getBookId()))
                        .collect(Collectors.joining(",", "[", "]")))
                .toString();
    }
}