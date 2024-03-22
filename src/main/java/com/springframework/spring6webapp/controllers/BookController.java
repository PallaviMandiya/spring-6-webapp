package com.springframework.spring6webapp.controllers;

import com.springframework.spring6webapp.domain.Author;
import com.springframework.spring6webapp.domain.Book;
import com.springframework.spring6webapp.domain.Publisher;
import com.springframework.spring6webapp.dtos.AuthorDTO;
import com.springframework.spring6webapp.dtos.BookDTO;
import com.springframework.spring6webapp.dtos.PublisherDTO;
import com.springframework.spring6webapp.mapper.BookMapper;
import com.springframework.spring6webapp.response.ResponseHandler;
import com.springframework.spring6webapp.services.AuthorService;
import com.springframework.spring6webapp.services.BookService;
import com.springframework.spring6webapp.services.PublisherService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private PublisherService publisherService;

    @GetMapping(value = "/getAllBooks")
    public ResponseEntity<Object> getBooks(HttpServletRequest request){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", request.getRequestURI());
        return ResponseHandler.responseBuilder("All Books", HttpStatus.OK, headers, bookService.findAll());
    }

    @GetMapping(value = "/search/bookById/{id}")
    public ResponseEntity<Object> getBookById(@PathVariable Long id, HttpServletRequest request){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", request.getRequestURI());
        return ResponseHandler.responseBuilder("Returned a Book Object", HttpStatus.OK, headers, bookService.getBookById(id));
    }

    @GetMapping(value = "/search/bookByTitle")
    public ResponseEntity<Object> getBookByTitle(@RequestParam("title") String title, HttpServletRequest request){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", request.getRequestURI());
        if(bookService.getBookByTitle(title).isEmpty()){
            return ResponseHandler.responseBuilder("No Title Match", HttpStatus.NOT_FOUND, headers, bookService.getBookByTitle(title));
        }
        return ResponseHandler.responseBuilder("Returns Books Matched By Title", HttpStatus.OK, headers, bookService.getBookByTitle(title));
    }

    @PostMapping(value = "/saveBook")
    public ResponseEntity saveBook(@RequestBody BookDTO book, HttpServletRequest request){
        BookDTO bookDTO = bookService.saveBook(book);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", request.getRequestURI() + "/" + bookDTO.getId());
        return ResponseHandler.responseBuilder("Book Saved", HttpStatus.CREATED, headers, bookDTO);
    }

    @PutMapping(value = "/updateBook/{id}")
    public ResponseEntity updateBook(@PathVariable Long id,@RequestBody BookDTO book, HttpServletRequest request){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", request.getRequestURI());
        if(bookService.updateBook(id, book)){
            bookService.updateBook(id, book);
        return ResponseHandler.responseBuilder("Book Updated Successfully", HttpStatus.OK,headers);
        }
        return ResponseHandler.responseBuilder("Book Not Found", HttpStatus.NOT_FOUND,headers);
    }

    @DeleteMapping(value = "/deleteBook/{id}")
    public ResponseEntity deleteBook(@PathVariable("id") Long id, HttpServletRequest request){
        if(bookService.deleteBook(id)){

            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", request.getRequestURI());
            return ResponseHandler.responseBuilder("Book Deleted", HttpStatus.OK, headers);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", request.getRequestURI());
        return ResponseHandler.responseBuilder("Book Not Found For Id: "+ id.toString(), HttpStatus.OK, headers);
    }

    @PatchMapping(value = "/patchBook/{id}")
    public ResponseEntity patchBook(@PathVariable Long id,
                                    @RequestBody BookDTO book, HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", request.getRequestURI());

        if(bookService.patchBook(id, book)){
            bookService.patchBook(id, book);
            return ResponseHandler.responseBuilder("Book Patched Successfully", HttpStatus.OK, headers);
        }
        return ResponseHandler.responseBuilder("Book Not Found", HttpStatus.NOT_FOUND, headers);
    }

    @PostMapping(value = "/setAuthor")
    public ResponseEntity setAuthor(@RequestParam("aid") Long aid,@RequestParam("bid") Long bid, HttpServletRequest request){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", request.getRequestURI());
        return ResponseHandler.responseBuilder(bookService.setAuthor(aid, bid), HttpStatus.OK, headers);
    }

    @PostMapping(value = "/setPublisher")
    public ResponseEntity setPublisher(@RequestParam("pid") Long pid,
                                       @RequestParam("bid") Long bid,
                                       HttpServletRequest request){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", request.getRequestURI());

        Optional<BookDTO> optionalBook = bookService.getBookById(bid);
        Optional<PublisherDTO> optionalPublisher = publisherService.getPublisherById(pid);

        if (optionalBook.isPresent() && optionalPublisher.isPresent()){
            if(bookService.getBookById(bid).get().getPublisher() == null){
                bookService.setPublisher(pid, bid);
                return ResponseHandler.responseBuilder("Existing Publisher Assigned To Book", HttpStatus.OK, headers);
            }
            return ResponseHandler.responseBuilder("Book Already Contains A Publisher", HttpStatus.OK, headers);
        }
        return ResponseHandler.responseBuilder("Book Or Publisher Not Found", HttpStatus.NOT_FOUND, headers);
    }
}
