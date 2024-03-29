package com.springframework.spring6webapp.repository;

import com.springframework.spring6webapp.domain.Author;
import com.springframework.spring6webapp.dtos.AuthorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends JpaRepository<Author,Long> {
    Page<Author> findByFirstnameIsLikeIgnoreCase(String firstname, Pageable pageable);

    Page<Author> findByLastnameIsLikeIgnoreCase(String lastname, Pageable pageable);

    Page<Author> findByFirstnameAndLastnameIsLikeIgnoreCase(String firstname, String lastname, Pageable pageable);
}
