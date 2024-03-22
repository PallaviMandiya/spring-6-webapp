package com.springframework.spring6webapp.services;

import com.springframework.spring6webapp.domain.Author;
import com.springframework.spring6webapp.dtos.AuthorDTO;

import java.util.Optional;

public interface AuthorService {
    Iterable<AuthorDTO> findAll();

    void saveAuthor(AuthorDTO author);

    boolean updateAuthor(Long id, AuthorDTO author);

    boolean deleteAuthor(Long id);

    Optional<AuthorDTO> getAuthorById(Long id);

    boolean patchAuthor(Long id, AuthorDTO author);
}
