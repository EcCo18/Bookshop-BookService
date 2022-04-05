package com.example.userservice.mapper;

import com.example.userservice.model.User;
import com.example.userservice.model.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMapper {

    public UserDto mapUserToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .lastName(user.getLastName())
                .build();
    }

    public List<UserDto> mapUserListToDtoList(List<User> userList) {
        return userList.stream().map(this::mapUserToDto).toList();
    }

    public User mapDtoToUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .lastName(userDto.getLastName())
                .build();
    }

    public List<User> mapDtoListToUserList(List<UserDto> userDtoList) {
        return userDtoList.stream().map(this::mapDtoToUser).toList();
    }
}
