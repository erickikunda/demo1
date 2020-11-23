package com.example.demo.domain.author;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface AuthorRepository extends JpaRepository<Author,Long> {

    Author findAuthorByName(String name);


    @Query(value = "select a from Author a where a.name like :keyword" )
    List<Author> fetchByAuthorContaining(@Param("keyword")  String keyword);
}
