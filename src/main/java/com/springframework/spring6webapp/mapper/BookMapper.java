package com.springframework.spring6webapp.mapper;

import com.springframework.spring6webapp.domain.Book;
import com.springframework.spring6webapp.dtos.BookDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BookMapper {
    Book bookDtoTOBook(BookDTO DTO);
    BookDTO bookToBookDto(Book book);
}
