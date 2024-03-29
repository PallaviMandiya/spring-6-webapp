package com.springframework.spring6webapp.services;

import com.springframework.spring6webapp.dtos.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserResponse {
    private List<UserDTO> userDTOList;

    private String message;
}
