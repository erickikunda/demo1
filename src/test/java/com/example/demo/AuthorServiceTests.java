package com.example.demo;

import com.example.demo.domain.author.Author;
import com.example.demo.domain.author.AuthorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorServiceTests {

    private final Logger logger = LoggerFactory.getLogger(AuthorServiceTests.class);

    @Autowired
    private AuthorService authorService;

    @Test
    public void addAuthorTest() {
        //given
        Author author = new Author("testAuthor");
        //when
        author = authorService.addAuthor(author);
        //then
        assertThat(author.getName()).isEqualTo("testAuthor");
        assertThat(author.getId()).isNotNull();
        logger.debug("testing addAuthorTest completed ok");
    }

}
