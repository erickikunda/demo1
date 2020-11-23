package com.example.demo;

import com.example.demo.domain.author.Author;
import com.example.demo.domain.author.AuthorRepository;
import com.example.demo.domain.book.Book;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.Reader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JdbcCommandLineRunner implements CommandLineRunner {

    private static Logger logger = LoggerFactory.getLogger(JdbcCommandLineRunner.class);

    //private final JdbcTemplate jdbcTemplate;
    private final AuthorRepository authorRepository;

    @Autowired
    public JdbcCommandLineRunner(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        logger.info("started uploadAuthorsFromCsv");
        uploadAuthorsFromCsv();
        logger.info("done uploadAuthorsFromCsv");
        List<Author> authors = authorRepository.findAll();

        if (authors == null || authors.isEmpty()) {
            List<Author> authorList = Stream.of("eric", "brian").map(name -> {

                Author author = new Author(name);

                /*Stream.of("1","2","3").forEach( c -> {
                    books.add(new Book( (String.join(author.getName(), " Book # ", c)) , author));
                });*/

                /*Set<Book> books  = Stream.of("1","2","3").map(n ->{
                   return new Book( (author.getName()+" Book # " + n) , author);
                }).collect(Collectors.toSet());*/


                author.setBooks(getRandomBooks(author));

                //  authorRepository.saveAndFlush(author);
                return author;

            }).collect(Collectors.toList());


            authorRepository.saveAll(authorList);

        }

        authors.forEach((author -> {
            logger.debug("{}", author);
        }));
       /* List<Author> authorList = Stream.of("eric","brian").map(name ->{
            return new Author(name);
        }).collect(Collectors.toList());*/

        // authorRepository.saveAll(authorList);

//        String authorSampleData ="insert into author values ('eric'),('brian')";
//        jdbcTemplate.execute(authorSampleData);

//        String createAccountTable = "CREATE TABLE IF NOT EXISTS " +
//                "account(" +
//                "id BIGSERIAL, " +
//                "account_status VARCHAR(25), " +
//                "created_date DATE , " +
//                "PRIMARY KEY (id)) ";
//
//        jdbcTemplate.execute(createAccountTable);

    }

    private Set<Book> getRandomBooks(final Author author) {
        Set<Book> books = new HashSet<>();

        int n = 3;
        for (int i = 0; i < n; i++) {
            books.add(new Book((author.getName() + " Book # " + i), author));
        }
        logger.debug("at getRandomBooks return : " + books.size());
        return books;
    }

    public List<Author> uploadAuthorsFromCsv() {
        logger.info("running uploadAuthorsFromCsv ...");
        List<Author> authors = new ArrayList<>();
        try {

            URI uri = ClassLoader.getSystemResource("csv/author.csv").toURI();
            Reader reader = Files.newBufferedReader(Paths.get(uri));

            CsvToBean<Author> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(Author.class).build();

            List<Author> authorList = csvToBean.parse();

            for (Author author : authorList) {
                logger.info("Parsed : " + author.toString());
            }


        } catch (Exception exception) {
            logger.error("author-from-csv : " + exception.getMessage());
        }
        return authors;
    }

}
