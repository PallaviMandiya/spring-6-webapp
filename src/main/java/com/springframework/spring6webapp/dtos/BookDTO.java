package com.springframework.spring6webapp.dtos;

import com.springframework.spring6webapp.domain.Author;
import com.springframework.spring6webapp.domain.Publisher;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
public class BookDTO {
    private long id;
    private String title;
    private String isbn;
    private Set<AuthorDTO> authors = new HashSet<>();
    private PublisherDTO publisher;
}
