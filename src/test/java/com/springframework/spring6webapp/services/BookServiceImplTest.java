package com.springframework.spring6webapp.services;

import com.springframework.spring6webapp.domain.Book;
import com.springframework.spring6webapp.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookServiceImplTest {
    @Autowired
    BookServiceImpl bookServiceImpl;

    @MockBean
    BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    public List<Book> testBookList(){
        Book testBook = Book.builder().title("Test Book").isbn("3847347").build();
        List<Book> bookList = new ArrayList<>();
        bookList.add(testBook);
        return bookList;
    }
    public Book testBook(Long id){
        return Book.builder().title("Test Book").isbn("3847347").build();
    }

    @Test
    void getBookByTitleTest() {
        when(bookRepository.findByTitleIsLikeIgnoreCase(any())).thenReturn(testBookList());
        assertNotNull(bookServiceImpl.getBookByTitle("Study"));
    }

    @Test
    void findAllTest() {
        when(bookRepository.findAll()).thenReturn(testBookList());
        assertThat(bookServiceImpl.findAll()).size().isEqualTo(1);
    }

    @Test
    void getBookByIdTest() {
        Long id = 212L;
        when(bookRepository.findById(any())).thenReturn(Optional.ofNullable(testBook(id)));
        Optional<Book> book = bookRepository.findById(id);
        assertNotNull(book);
    }
}