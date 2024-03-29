package com.springframework.spring6webapp.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "user_tbl")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long uid;
    @NonNull
    @Column(unique = true)
    private String username;
    @NonNull
    private String password;
    @ManyToMany
    @JoinTable(name="user_book", joinColumns = @JoinColumn(name="user_id"), inverseJoinColumns = @JoinColumn(name="book_id"))
    private List<Book> books = new ArrayList<>();
}
