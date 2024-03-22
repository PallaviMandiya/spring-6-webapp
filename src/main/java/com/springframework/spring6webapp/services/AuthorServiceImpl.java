package com.springframework.spring6webapp.services;

import com.springframework.spring6webapp.domain.Author;
import com.springframework.spring6webapp.dtos.AuthorDTO;
import com.springframework.spring6webapp.mapper.AuthorMapper;
import com.springframework.spring6webapp.repository.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class AuthorServiceImpl implements AuthorService{

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    AuthorMapper authorMapper;

    @Override
    public Iterable<AuthorDTO> findAll() {
        List<AuthorDTO> authorDTOS = new ArrayList<>();
        for(Author author : authorRepository.findAll()){
            authorDTOS.add(authorMapper.authorToAuthorDto(author));
        }
        return authorDTOS;
    }

    @Override
    public void saveAuthor(AuthorDTO author) {
        authorRepository.save(authorMapper.authorDtoToAuthor(author));
    }

    @Override
    public boolean updateAuthor(Long id, AuthorDTO authorDTO) {
        Optional<Author> authorOptional = authorRepository.findById(id);
        if(authorOptional.isPresent()) {
            Author author = authorOptional.get();
            author.setFirstname(authorDTO.getFirstname());
            author.setLastname(authorDTO.getLastname());
            authorRepository.save(author);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteAuthor(Long id) {
        if(authorRepository.existsById(id)){
            authorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<AuthorDTO> getAuthorById(Long id) {
        return Optional.ofNullable(authorMapper.authorToAuthorDto(authorRepository.findById(id).orElse(null)));
    }

    @Override
    public boolean patchAuthor(Long id, AuthorDTO authorDTO) {
        Optional<Author> authorOptional = authorRepository.findById(id);
        if(authorOptional.isPresent()){
            Author author = authorOptional.get();
            if(StringUtils.hasText(authorDTO.getFirstname())){
                author.setFirstname(authorDTO.getFirstname());
            }
            if(StringUtils.hasText(authorDTO.getLastname())){
                author.setLastname(authorDTO.getLastname());
            }
            authorRepository.save(author);
            return true;
        }
        return false;
    }
}
