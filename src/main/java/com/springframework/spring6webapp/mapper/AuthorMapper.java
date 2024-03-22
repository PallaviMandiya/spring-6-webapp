package com.springframework.spring6webapp.mapper;

import com.springframework.spring6webapp.domain.Author;
import com.springframework.spring6webapp.dtos.AuthorDTO;
import org.mapstruct.Mapper;

@Mapper
public interface AuthorMapper {
    Author authorDtoToAuthor(AuthorDTO author);
    AuthorDTO authorToAuthorDto(Author author);
}
