package com.springframework.spring6webapp.mapper;

import com.springframework.spring6webapp.domain.User;
import com.springframework.spring6webapp.dtos.UserDTO;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User UserDtoToUser(UserDTO dto);
    UserDTO UserToUserDto(User user);
}
