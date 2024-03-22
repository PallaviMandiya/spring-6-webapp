package com.springframework.spring6webapp.repository;

import com.springframework.spring6webapp.domain.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book,Long> {

    List<Book> findByTitleIsLikeIgnoreCase(String title);

//    @Query(value = "SELECT * FROM book where title REGEXP :title", nativeQuery = true)
//    List<Book> getAllBooks(String title);
}
