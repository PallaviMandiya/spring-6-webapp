package com.springframework.spring6webapp.controllers;

import com.springframework.spring6webapp.domain.Book;
import com.springframework.spring6webapp.domain.Publisher;
import com.springframework.spring6webapp.dtos.PublisherDTO;
import com.springframework.spring6webapp.response.ResponseHandler;
import com.springframework.spring6webapp.services.BookService;
import com.springframework.spring6webapp.services.PublisherService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/publisher")
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    @Autowired
    private BookService bookService;

    @GetMapping(value = "/allPublisher")
    public ResponseEntity<Object> getAllPublisher(HttpServletRequest request){

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", request.getRequestURI());
        return ResponseHandler.responseBuilder("All Publisher", HttpStatus.OK, headers, publisherService.getAllPublisher());
    }

    @GetMapping(value = "/search/publisherByName")
    public ResponseEntity<Object> getPublisherByName(@RequestParam("publisherName") String publisherName, HttpServletRequest request){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", request.getRequestURI());
        if(publisherService.getPublisherByName(publisherName).isEmpty()){
            return ResponseHandler.responseBuilder("No Publisher Matched", HttpStatus.NOT_FOUND, headers, publisherService.getPublisherByName(publisherName));
        }
        return ResponseHandler.responseBuilder("Returns Publishers Matched By PublisherName", HttpStatus.OK, headers, publisherService.getPublisherByName(publisherName));
    }

    @GetMapping(value = "/search/publisherById/{id}")
    public ResponseEntity<Object> getPublisherById(@PathVariable Long id, HttpServletRequest request){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", request.getRequestURI());
        if(publisherService.getPublisherById(id).isEmpty()){
            return ResponseHandler.responseBuilder("Publisher Not Found", HttpStatus.NOT_FOUND, headers);
        }
        return ResponseHandler.responseBuilder("Publisher Entity", HttpStatus.OK, headers, publisherService.getPublisherById(id));
    }

    @PostMapping(value = "/savePublisher")
    public ResponseEntity<Object> savePublisher(@RequestBody PublisherDTO publisher, HttpServletRequest request){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", request.getRequestURI());
        publisherService.savePublisher(publisher);
        return ResponseHandler.responseBuilder("Publisher Saved", HttpStatus.CREATED, headers);
    }

    @PutMapping(value = "/updatePublisher/{id}")
    public ResponseEntity updatePublisher(@PathVariable("id") Long id,
                                          @RequestBody PublisherDTO publisher,
                                          HttpServletRequest request){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", request.getRequestURI());

        if(publisherService.updatePublisher(id, publisher)){
            publisherService.updatePublisher(id, publisher);
            return ResponseHandler.responseBuilder("Publisher Updated Successfully", HttpStatus.OK, headers);
        }
        return ResponseHandler.responseBuilder("Publisher Not Found", HttpStatus.NOT_FOUND, headers);
    }

    @DeleteMapping(value = "/deletePublisher/{id}")
    public ResponseEntity deletePublisher(@PathVariable Long id, HttpServletRequest request){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", request.getRequestURI());
        if(publisherService.deletePublisher(id)){
            publisherService.deletePublisher(id);
            return ResponseHandler.responseBuilder("Publisher Deleted", HttpStatus.OK, headers);
        }
        return ResponseHandler.responseBuilder("Publisher Not Found", HttpStatus.NOT_FOUND, headers);
    }

    @PatchMapping(value = "/patchPublisher/{id}")
    public ResponseEntity patchPublisher(@PathVariable Long id,
                                         @RequestBody PublisherDTO publisher,
                                         HttpServletRequest request){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", request.getRequestURI());
        if(publisherService.patchPublisher(id, publisher)) {
            publisherService.patchPublisher(id, publisher);
            return ResponseHandler.responseBuilder("Publisher Patched Successfully", HttpStatus.OK, headers);
        }
        return ResponseHandler.responseBuilder("Publisher Not Found", HttpStatus.NOT_FOUND, headers);
    }
}
