package com.example.demo.domain.author;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface AuthorService {

    Author getAuthorByName(String name);


    List<Author> getAuthors();

    Author addAuthor(Author author);

    List<Author> addAuthors(List<Author> authorList);

    void deleteAuthorById(Long id);

    Author updateAuthor(Author author);

    Author getAuthorById(Long id) throws EntityNotFoundException;

    List<Author> fetchByAuthorNameContaining(String keyword);
}
