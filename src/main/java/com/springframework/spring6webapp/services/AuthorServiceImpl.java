package com.springframework.spring6webapp.services;

import com.springframework.spring6webapp.domain.Author;
import com.springframework.spring6webapp.domain.Book;
import com.springframework.spring6webapp.dtos.AuthorDTO;
import com.springframework.spring6webapp.mapper.AuthorMapper;
import com.springframework.spring6webapp.repository.AuthorRepository;
import com.springframework.spring6webapp.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class AuthorServiceImpl implements AuthorService{

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorMapper authorMapper;
    @Autowired
    private BookRepository bookRepository;

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 5;

    @Override
    public Iterable<AuthorDTO> getAll() {
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

    @Transactional
    @Override
    public boolean deleteAuthor(Long id) {
        if(authorRepository.existsById(id)){
            Author author = authorRepository.findById(id).get();
            for(Book book: bookRepository.findBooksByAuthors(author)){
                bookRepository.deleteById(book.getId());
            }
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

    @Override
    public Page<AuthorDTO> listAuthors(String firstname, String lastname, Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
        Page<Author> authorPage;

        if(StringUtils.hasText(firstname)){
            authorPage = listAuthorByFirstname(firstname, pageRequest);
        } else if (!StringUtils.hasText(firstname) && StringUtils.hasText(lastname)) {
            authorPage = listAuthorByLastname(lastname, pageRequest);
        } else if (StringUtils.hasText(firstname) && StringUtils.hasText(lastname)) {
            authorPage = listAuthorByFirstnameAndLastname(firstname, lastname, pageRequest);
        }else{
            authorPage = authorRepository.findAll(pageRequest);
        }
        return authorPage.map(authorMapper::authorToAuthorDto);
    }

    public static PageRequest buildPageRequest(Integer pageNumber, Integer pageSize) {
        int queryPageNumber;
        int queryPageSize;

        if(pageNumber != null && pageNumber > 0){
            queryPageNumber = pageNumber - 1;
        }else{
            queryPageNumber = DEFAULT_PAGE;
        }

        if(pageSize > 1000){
            queryPageSize = 1000;
        }else {
            queryPageSize = DEFAULT_PAGE_SIZE;
        }
        Sort sort = Sort.by(Sort.Order.asc("firstname"));
        return PageRequest.of(queryPageNumber,queryPageSize, sort);
    }

    public Page<Author> listAuthorByFirstnameAndLastname(String firstname, String lastname, Pageable pageable) {
        return authorRepository.findByFirstnameAndLastnameIsLikeIgnoreCase("%" + firstname + "%", "%" + lastname + "%", pageable);
    }

    public Page<Author> listAuthorByLastname(String lastname, Pageable pageable) {
        return authorRepository.findByLastnameIsLikeIgnoreCase("%" + lastname + "%", pageable);
    }

    public Page<Author> listAuthorByFirstname(String firstname, Pageable pageable) {
        return authorRepository.findByFirstnameIsLikeIgnoreCase("%" + firstname + "%", pageable);
    }

}
