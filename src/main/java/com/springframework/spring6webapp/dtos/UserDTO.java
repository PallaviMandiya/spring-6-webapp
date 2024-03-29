package com.springframework.spring6webapp.dtos;

import com.springframework.spring6webapp.domain.Book;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class UserDTO {
    private Long uid;
    private String username;
    private String password;
    private List<BookDTO> books = new ArrayList<>();
}
