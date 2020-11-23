package com.example.demo.domain.author;

import com.example.demo.domain.book.Book;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.opencsv.bean.CsvBindByName;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;
@Entity
public class Author implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_generator")
    @SequenceGenerator(name = "author_generator", sequenceName = "author_id_seq", allocationSize = 1)
    @CsvBindByName
    private Long id;

    @Size(min=3, max=20)
    @CsvBindByName
    private String name;

    @OneToMany(targetEntity = Book.class,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Book> books = new HashSet<>();

    public Author() {
    }

    public Author(Long id, String name, Set<Book> books) {
        this.id = id;
        this.name = name;
        this.books = books;
    }

    public Author(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public boolean addBook(Book book) {
        book.setAuthorIdFk(this);
        return getBooks().add(book);
    }

    public boolean removeBook(Book book) {
        book.setAuthorIdFk(null);
        return getBooks().remove(book);
    }

    public void removeBooks() {
        Iterator<Book> bookIterator = books.iterator();

       bookIterator.forEachRemaining(book -> {
            book.setAuthorIdFk(null);
            bookIterator.remove();
        });
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(id, author.id) &&
                Objects.equals(name, author.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
