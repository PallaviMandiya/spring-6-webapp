package com.springframework.spring6webapp.repository;

import com.springframework.spring6webapp.domain.Author;
import com.springframework.spring6webapp.domain.Book;
import com.springframework.spring6webapp.dtos.AuthorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {

    List<Book> findByTitleIsLikeIgnoreCase(String title);

    Page<Book> findAllByTitleIsLikeIgnoreCase(String title, Pageable pageable);

    List<Book> findByPublisherId(Long id);

    List<Book> findBooksByAuthors(Author author);
    //    Page<Book> findAll(Pageable pageable);


//    @Query(value = "SELECT * FROM book where title REGEXP :title", nativeQuery = true)
//    List<Book> getAllBooks(String title);
}
