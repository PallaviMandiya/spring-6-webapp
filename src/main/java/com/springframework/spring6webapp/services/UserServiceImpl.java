package com.springframework.spring6webapp.services;

import com.springframework.spring6webapp.domain.Book;
import com.springframework.spring6webapp.domain.User;
import com.springframework.spring6webapp.dtos.UserDTO;
import com.springframework.spring6webapp.mapper.UserMapper;
import com.springframework.spring6webapp.repository.BookRepository;
import com.springframework.spring6webapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Iterable<UserDTO> findAll() {
        List<UserDTO> userDTOList = new ArrayList<>();
        for(User user: userRepository.findAll()){
            userDTOList.add(userMapper.UserToUserDto(user));
        }
        return userDTOList;
    }

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        UserDTO userDTO1 = userMapper.UserToUserDto(userRepository.save(userMapper.UserDtoToUser(userDTO)));
        return userDTO1;
    }

    @Override
    public boolean updateUser(long uid,UserDTO userDTO) {
        Optional<User> userInDb = userRepository.findById(uid);
        if(userInDb.isPresent()){
            User user = userInDb.get();
            user.setUsername(userDTO.getUsername());
            user.setPassword(userDTO.getPassword());
            userRepository.save(user);
            Optional<User> savedUser = userRepository.findById(uid);
            return true;
        }
        return false;
    }
    @Override
    public boolean patchUser(long uid,UserDTO userDTO) {
        Optional<User> userInDb = userRepository.findById(uid);
        if(userInDb.isPresent()){
            User user = userInDb.get();
            if(StringUtils.hasText(userDTO.getUsername())){
                user.setUsername(userDTO.getUsername());
            }
            if(StringUtils.hasText(userDTO.getPassword())){
                user.setPassword(userDTO.getPassword());
            }
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public String setBook(Long bid, Long uid) {
        Optional<Book> optionalBook = bookRepository.findById(bid);
        Optional<User> optionalUser = userRepository.findById(uid);

        if(optionalBook.isPresent() && optionalUser.isPresent()){
            Book book = optionalBook.get();
            User user = optionalUser.get();

            if(user.getBooks().stream().anyMatch(a -> a.getId() == bid)){
                return "User Already Contains This Book";
            }
            List<Book> books = user.getBooks();
            books.add(book);
            user.setBooks(books);
            UserDTO userDTO = userMapper.UserToUserDto(user);
            updateUser(userDTO.getUid(), userDTO);
            return "Existing Book Assigned To User";
        }
        return "Book Or User Not Found";
    }

    @Override
    public boolean deleteUser(Long id) {
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<UserDTO> getUserById(Long id) {
        return Optional.ofNullable(userMapper.UserToUserDto(userRepository.findById(id).orElse(null)));
    }

    @Override
    public UserResponse getUserByBook(String title) {
        List<UserDTO> userDTOList = new ArrayList<>();
        List<Book> book = bookRepository.findByTitleIsLikeIgnoreCase("%" + title + "%");

        if(!book.isEmpty()) {
            for(User user:  userRepository.findByTitle(title)){
                userDTOList.add(userMapper.UserToUserDto(user));
            }
            return new UserResponse(userDTOList, "Book Found In DB");
        }
        return new UserResponse(userDTOList, "Book Not Found In DB");
    }
}
