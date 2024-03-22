package com.springframework.spring6webapp.services;

import com.springframework.spring6webapp.domain.Author;
import com.springframework.spring6webapp.domain.Book;
import com.springframework.spring6webapp.domain.Publisher;
import com.springframework.spring6webapp.dtos.AuthorDTO;
import com.springframework.spring6webapp.dtos.BookDTO;
import com.springframework.spring6webapp.mapper.AuthorMapper;
import com.springframework.spring6webapp.mapper.BookMapper;
import com.springframework.spring6webapp.repository.AuthorRepository;
import com.springframework.spring6webapp.repository.BookRepository;
import com.springframework.spring6webapp.repository.PublisherRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private AuthorMapper authorMapper;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private PublisherRepository publisherRepository;


    @Override
    public Iterable<BookDTO> findAll() {
        List<BookDTO> bookDTO = new ArrayList<>();
        for(Book book : bookRepository.findAll()){
            bookDTO.add(bookMapper.bookToBookDto(book));
        }
        return bookDTO;
    }


    @Override
    public BookDTO saveBook(BookDTO book) {
        return bookMapper.bookToBookDto(bookRepository.save(bookMapper.bookDtoTOBook(book)));
    }

    @Override
    public boolean updateBook(Long id, BookDTO bookDTO) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if(optionalBook.isPresent()){
            Book book = optionalBook.get();
            book.setTitle(bookDTO.getTitle());
            book.setIsbn(bookDTO.getIsbn());
            bookRepository.save(book);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteBook(Long id) {
        if(bookRepository.existsById(id)){
            bookRepository.deleteById(id);
            System.out.println("True");
            return true;
        }
        System.out.println("False");
        return false;
    }

    @Override
    public Optional<BookDTO> getBookById(Long bid) {
        return Optional.ofNullable(bookMapper.bookToBookDto(bookRepository.findById(bid).orElse(null)));
    }

    @Override
    public boolean patchBook(Long id, BookDTO bookDTO) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if(optionalBook.isPresent()){
            Book book = optionalBook.get();

            if(StringUtils.hasText(bookDTO.getTitle())){
                book.setTitle(bookDTO.getTitle());
            }
            if(StringUtils.hasText(bookDTO.getIsbn())){
                book.setIsbn(bookDTO.getIsbn());
            }
            bookRepository.save(book);
            return true;
        }
        return false;
    }

    @Override
    public String setAuthor(Long aid, Long bid) {
        boolean flag = false;

        Optional<Book> optionalBook = bookRepository.findById(bid);
        Optional<Author> optionalAuthor = authorRepository.findById(aid);

        if (optionalBook.isPresent() && optionalAuthor.isPresent()) {
            Book book = optionalBook.get();
            Author author = optionalAuthor.get();

            if(book.getAuthors().stream().anyMatch(a -> a.getId() == aid )){
                return "Book Already Contains This Author";
            }
            Set<Author> authors = book.getAuthors();
            authors.add(author);

            book.setAuthors(authors);

            BookDTO bookDTO = bookMapper.bookToBookDto(book);
            updateBook(bid, bookDTO);
            return "Existing Author Assigned To Book";
        }
        return "Book Or Author Not Found";
    }

    @Override
    public void setPublisher(Long pid, Long bid) {
        Optional<Book> optionalBook = bookRepository.findById(bid);
        Optional<Publisher> optionalPublisher = publisherRepository.findById(pid);

        Book book = optionalBook.get();
        Publisher publisher = optionalPublisher.get();
        book.setPublisher(publisher);

            BookDTO bookDTO = bookMapper.bookToBookDto(book);
            updateBook(bid, bookDTO);
            }

    @Override
    public List<BookDTO> getBookByTitle(String title) {
        List<BookDTO> bookDTOS = new ArrayList<>();
        for(Book book: bookRepository.findByTitleIsLikeIgnoreCase("%" + title + "%")){
            bookDTOS.add(bookMapper.bookToBookDto(book));
        }
        //        List<Book> books = bookRepository.getAllBooks(title);
        return bookDTOS;
    }
}