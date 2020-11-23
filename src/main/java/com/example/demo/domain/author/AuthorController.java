package com.example.demo.domain.author;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/author")
public class AuthorController {

    private AuthorService authorService;

    private final Logger logger = LoggerFactory.getLogger(AuthorController.class);

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }


    @GetMapping(value = "/list")
    @ResponseStatus(HttpStatus.OK)
    public List<Author> findAll() {
        return authorService.getAuthors();
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Author add(@RequestBody Author author) {
        return authorService.addAuthor(author);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        authorService.deleteAuthorById(id);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Author findById(@PathVariable Long id) throws EntityNotFoundException {
        return authorService.getAuthorById(id);
    }

    @PutMapping(value = "/update")
    @ResponseStatus(HttpStatus.OK)
    public Author update(@RequestBody Author author) {
        return authorService.updateAuthor(author);
    }

    @GetMapping(value = "/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public Author findByName(@PathVariable String name) {
        return authorService.getAuthorByName(name);
    }

    @GetMapping(value = "/name-containing/{keyword}")
    @ResponseStatus(HttpStatus.OK)
    public List<Author> fuzzyFindByname(@PathVariable String keyword) throws EntityNotFoundException {
        return authorService.fetchByAuthorNameContaining(keyword);
    }

    @PostMapping(value = "/author-from-csv")
    public List<Author> uploadAuthorsFromCsv(@RequestParam("file") MultipartFile multipartFile) {

        List<Author> authors = new ArrayList<>();
        try {
            Reader reader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()));
            CsvToBean<Author> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(Author.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            authors = csvToBean.parse();
            logger.info("parsed authors as : ");
            authors.stream().forEach(author -> logger.info(author.toString()));

        } catch (Exception exception) {
            logger.error("author-from-csv : " + exception.getMessage());
        }
        return authors;


    }
}


