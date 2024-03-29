package com.springframework.spring6webapp.controllers;

import com.springframework.spring6webapp.domain.Book;
import com.springframework.spring6webapp.domain.User;
import com.springframework.spring6webapp.dtos.BookDTO;
import com.springframework.spring6webapp.dtos.UserDTO;
import com.springframework.spring6webapp.response.ResponseHandler;
import com.springframework.spring6webapp.services.BookService;
import com.springframework.spring6webapp.services.UserResponse;
import com.springframework.spring6webapp.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @GetMapping(value = "/getAllUsers")
    public ResponseEntity getUsers(HttpServletRequest request){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", request.getRequestURI());
        return ResponseHandler.responseBuilder("All Users", HttpStatus.OK, headers, userService.findAll());
    }

    @GetMapping(value = "/userById/{uid}")
    public ResponseEntity getUserById(@PathVariable Long uid, HttpServletRequest request){
        Optional<UserDTO> userDTO = userService.getUserById(uid);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", request.getRequestURI() + "/" + uid);
        if(!userDTO.isEmpty()){
            return ResponseHandler.responseBuilder("User Fetched", HttpStatus.OK, headers, userDTO);
        }
        return ResponseHandler.responseBuilder("User Not Found", HttpStatus.NOT_FOUND, headers);
    }

    @PostMapping(value = "/saveUser")
    public ResponseEntity saveUser(@RequestBody UserDTO user,HttpServletRequest request){
        UserDTO userDTO = userService.saveUser(user);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", request.getRequestURI() + "/" + userDTO.getUid());
        return ResponseHandler.responseBuilder("User Created", HttpStatus.CREATED, headers,  userDTO);
    }

    @PutMapping(value = "/updateUser/{uid}")
    public ResponseEntity updateUser(@PathVariable long uid,
                                     @RequestBody UserDTO user,
                                     HttpServletRequest request){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", request.getRequestURI() + "/" + uid);
        if(userService.updateUser(uid, user)){
            return ResponseHandler.responseBuilder("User Updated", HttpStatus.OK, headers);
        }else{
            return ResponseHandler.responseBuilder("User Not Found", HttpStatus.NOT_FOUND, headers);
        }
    }

    @PatchMapping(value = "/patchUser/{uid}")
    public ResponseEntity patchUser(@PathVariable long uid, @RequestBody UserDTO userDTO, HttpServletRequest request){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", request.getRequestURI() + "/" + uid);
        if(userService.patchUser(uid, userDTO)){
            return ResponseHandler.responseBuilder("User Patched", HttpStatus.OK, headers);
        }else{
            return ResponseHandler.responseBuilder("User Not Found", HttpStatus.NOT_FOUND, headers);
        }
    }

    @DeleteMapping(value = "/deleteUser/{uid}")
    public ResponseEntity deleteUser(@PathVariable Long uid){
        if(userService.deleteUser(uid)){
            return ResponseHandler.responseBuilder("User Deleted", HttpStatus.OK);
        }
        return ResponseHandler.responseBuilder("User Not Found", HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/setBook")
    public ResponseEntity assignBookToUser(@RequestParam Long bid,@RequestParam Long uid, HttpServletRequest request){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", request.getRequestURI() + "?bid=" + uid + "&uid=" + uid);

        return ResponseHandler.responseBuilder(userService.setBook(bid, uid), HttpStatus.OK, headers);
    }

    @GetMapping(value = "/getUserByBook")
    public ResponseEntity getUserByBook(@RequestParam String title, HttpServletRequest request){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", request.getRequestURI() + "/" + title);
        UserResponse userResponse = userService.getUserByBook(title);
        String msg = userResponse.getMessage();
        List<UserDTO> userList = userResponse.getUserDTOList();
        if(!userList.isEmpty()){
            return ResponseHandler.responseBuilder(msg, HttpStatus.OK, headers, userList);
        }else{
            return ResponseHandler.responseBuilder(msg, HttpStatus.OK, headers, userList);
        }
    }
}
