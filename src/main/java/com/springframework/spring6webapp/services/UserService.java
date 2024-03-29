package com.springframework.spring6webapp.services;

import com.springframework.spring6webapp.dtos.UserDTO;

import java.util.Optional;

public interface UserService {
    Iterable<UserDTO> findAll();
    UserDTO saveUser(UserDTO User);
    boolean updateUser(long uid, UserDTO userDTO);
    boolean patchUser(long uid, UserDTO userDTO);
//    boolean deleteUser(Long id);
//    Optional<UserDTO> getUserById(Long bid);
//    boolean patchUser(Long id, UserDTO User);

    String setBook(Long bid, Long uid);

    boolean deleteUser(Long uid);

//    Optional<UserDTO> getUserByUsername(String username);

    Optional<UserDTO> getUserById(Long uid);
    UserResponse getUserByBook(String title);
}
