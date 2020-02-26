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
@Table(name = "t_author")
@Access(AccessType.FIELD)
public class Author implements Serializable {

        @Id
        @Column(name="authorId")
        @SequenceGenerator(name = "author", sequenceName = "authorId", initialValue = 1, allocationSize = 1)
        @GeneratedValue(generator = "author")
    private long authorId;

        @Column(name="authorName", nullable = false)
        @NotNull
        @NotBlank
    private String authorName;

        @ManyToMany(mappedBy = "bookAuthorSet")
    private Set<Book> authorBookSet = new HashSet<>();

    public Author() {
    }

    public Author(@NotNull @NotBlank String authorName) {
        this.authorName = authorName;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Set<Book> getAuthorBookSet() {
        return authorBookSet;
    }

    public void setAuthorBookSet(Set<Book> authorBookSet) {
        this.authorBookSet = authorBookSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return getAuthorId() == author.getAuthorId() &&
                getAuthorName().equals(author.getAuthorName()) &&
                Objects.equals(getAuthorBookSet(), author.getAuthorBookSet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getAuthorId(),
                getAuthorName());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Author.class.getSimpleName() + "[", "]")
                .add("id=" + authorId)
                .add("name='" + authorName + "'")
                .add("bookSet=" +
                        authorBookSet.stream()
                        .map(book -> Long.toString(book.getBookId()))
                        .collect(Collectors.joining(",", "[", "]")))
                .toString();
    }
}
