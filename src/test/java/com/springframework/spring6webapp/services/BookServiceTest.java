package com.springframework.spring6webapp.services;

import com.springframework.spring6webapp.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookServiceTest {
    @Autowired
    BookRepository bookRepository;

    @Test
    void findAllBookTest() {
        assertNotNull(bookRepository.findAll());
    }
}