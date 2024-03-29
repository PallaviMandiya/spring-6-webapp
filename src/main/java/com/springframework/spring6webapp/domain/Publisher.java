package com.springframework.spring6webapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String publisherName;
    private String address;
    private String city;
    private String state;
    private String zipCode;

    @OneToMany(mappedBy = "publisher")
    @JsonIgnore
    private Set<Book> books;
}
