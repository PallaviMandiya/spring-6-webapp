package com.springframework.spring6webapp.controllers;

import com.springframework.spring6webapp.dtos.AuthorDTO;
import com.springframework.spring6webapp.response.ResponseHandler;
import com.springframework.spring6webapp.services.AuthorService;
import com.springframework.spring6webapp.services.BookService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @Autowired
    BookService bookService;

    @GetMapping(value = "/getAllAuthors")
    public ResponseEntity<Object> getAuthors(HttpServletRequest request){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", request.getRequestURI());
        return ResponseHandler.responseBuilder("All Authors", HttpStatus.OK, headers, authorService.findAll());
    }

    @GetMapping(value = "/search/authorById/{id}")
    public ResponseEntity<Object> getAuthorById(@PathVariable() Long id, HttpServletRequest request){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", request.getRequestURI());
        return ResponseHandler.responseBuilder("Author Entity", HttpStatus.OK, headers, authorService.getAuthorById(id));
    }

    @PostMapping(value = "/saveAuthor")
    public ResponseEntity saveAuthor(@RequestBody AuthorDTO author, HttpServletRequest request){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", request.getRequestURI());
        authorService.saveAuthor(author);
        return ResponseHandler.responseBuilder("Author Saved", HttpStatus.CREATED, headers);
    }

    @PutMapping(value = "/updateAuthor/{id}")
    public ResponseEntity updateAuthor(@PathVariable() Long id, @RequestBody AuthorDTO author, HttpServletRequest request){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", request.getRequestURI());
        if(authorService.updateAuthor(id, author)){
            authorService.updateAuthor(id, author);
            return ResponseHandler.responseBuilder("Author Updated Successfully", HttpStatus.OK, headers);
        }
        return ResponseHandler.responseBuilder("Author Not Found", HttpStatus.NOT_FOUND, headers);
    }

    @DeleteMapping(value = "/deleteAuthor/{id}")
    public ResponseEntity deleteAuthor(@PathVariable() Long id,HttpServletRequest request){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", request.getRequestURI());

        if(authorService.deleteAuthor(id)){
            authorService.deleteAuthor(id);
            return ResponseHandler.responseBuilder("Author Deleted", HttpStatus.OK, headers);
        }
        return ResponseHandler.responseBuilder("Author Not Found", HttpStatus.NOT_FOUND, headers);
    }

    @PatchMapping(value = "/patchAuthor/{id}")
    public ResponseEntity patchAuthor(@PathVariable Long id, @RequestBody AuthorDTO author, HttpServletRequest request){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", request.getRequestURI());

        if(authorService.patchAuthor(id, author)){
            authorService.patchAuthor(id, author);
            return ResponseHandler.responseBuilder("Author Patched Successfully",HttpStatus.OK, headers);
        }
        return ResponseHandler.responseBuilder("Author Not Found", HttpStatus.NOT_FOUND, headers);
    }
}
