package com.springframework.spring6webapp.services;

import com.springframework.spring6webapp.domain.Book;
import com.springframework.spring6webapp.dtos.BookDTO;
import com.springframework.spring6webapp.dtos.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;

import javax.print.DocFlavor;
import java.util.List;
import java.util.Optional;

public interface BookService {
    Iterable<BookDTO> findAll();
    BookDTO saveBook(BookDTO book);
    boolean updateBook(Long id, BookDTO book);
    boolean deleteBook(Long id);
    Optional<BookDTO> getBookById(Long bid);
    boolean patchBook(Long id, BookDTO book);

    String setAuthor(Long aid, Long bid);

    void setPublisher(Long pid, Long bid);

    List<BookDTO> getBookByTitle(String title);

    Page<BookDTO> bookList(String title, Integer pageNumber, Integer pageSize);
}
