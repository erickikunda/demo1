package com.example.demo;

import com.example.demo.domain.author.AuthorController;
import com.example.demo.domain.author.AuthorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthorServiceControllerTests {


   // @MockBean
   // private AuthorService authorService;
    @Autowired
    private MockMvc mvc;

    @Test
    public void getAuthorList() throws Exception {

         mvc.perform(get("/author/list"))
                .andDo(print())
                .andExpect(status().isOk());
                // .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON));
               // .andExpect((ResultMatcher) content().string(containsString("eric")));

    }
}
