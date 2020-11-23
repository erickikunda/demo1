package com.example.demo.domain.book;

import com.example.demo.domain.author.Author;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.opencsv.bean.CsvBindByName;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Book implements Serializable {

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_generator")
    @SequenceGenerator(name = "book_generator",sequenceName = "book_id_seq", allocationSize = 1)
    @CsvBindByName
    private Long id;

    @CsvBindByName
    private String title;

    @ManyToOne(targetEntity = Author.class)
    @JoinColumn(name = "author_id_fk")
    @JsonIgnoreProperties("books")
    @CsvBindByName(column = "author_id")
    private Author authorIdFk;

    public Book() {
    }
    public Book(String title) {
        this.title =title;
    }

    public Book(Long id, String title, Author authorIdFk) {
        this.id = id;
        this.title = title;
        this.authorIdFk = authorIdFk;
    }

    public Book(String title, Author authorIdFk) {
        this.title = title;
        this.authorIdFk = authorIdFk;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthorIdFk() {
        return authorIdFk;
    }

    public void setAuthorIdFk(Author author) {
        this.authorIdFk = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(title, book.title)
                && Objects.equals(id,book.id)
                && Objects.equals(authorIdFk.getId(),book.authorIdFk.getId())  ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
