package com.springframework.spring6webapp.dtos;

import com.springframework.spring6webapp.domain.Book;
import lombok.Data;

import java.util.Set;

@Data
public class PublisherDTO {
    private long id;
    private String publisherName;
    private String address;
    private String city;
    private String state;
    private String zipCode;
//    private Set<Book> books;
}
