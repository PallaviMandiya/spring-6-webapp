package com.springframework.spring6webapp.dtos;

import com.springframework.spring6webapp.domain.Book;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class AuthorDTO {
    private long id;
    private String firstname;
    private String lastname;
//    private Set<Book> books = new HashSet<>();
}
