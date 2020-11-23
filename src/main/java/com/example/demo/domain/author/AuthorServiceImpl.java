package com.example.demo.domain.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {


    private AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author getAuthorByName(String name) {

        return authorRepository.findAuthorByName(name);

    }

    @Override
    public List<Author> getAuthors() {

        List<Author> list = authorRepository.findAll(Sort.by("name"));

        //Collections.sort(list, Comparator.comparing(Author::getName));

        return list;
    }

    @Override
    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public void deleteAuthorById(Long id) {
        authorRepository.deleteById(id);
    }

    @Override
    public Author updateAuthor(Author author) {
        return authorRepository.saveAndFlush(author);
    }

    @Override
    @Transactional(readOnly = true)
    public Author getAuthorById(Long id) throws EntityNotFoundException {
        return authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }


    //    @Transactional(readOnly = true)
    @Override

    public List<Author> fetchByAuthorNameContaining(String keyword) {

        return authorRepository.fetchByAuthorContaining("%" + keyword + "%");
    }

    @Override
    public List<Author> addAuthors(List<Author> authorList) {
        return authorRepository.saveAll(authorList);
    }

}
