package com.springframework.spring6webapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
public class Author{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String firstname;
    private String lastname;
    @ManyToMany(mappedBy = "authors")
    @JsonIgnore
    private Set<Book> books = new HashSet<>();
}
