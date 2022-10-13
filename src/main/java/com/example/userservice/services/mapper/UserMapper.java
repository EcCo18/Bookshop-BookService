package com.example.userservice.services.mapper;

import com.example.userservice.model.User;
import com.example.userservice.model.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class UserMapper {

    public UserDto mapUserToDto(User user) {
        log.debug("mapping user to dto: " + user);
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .lastName(user.getLastName())
                .role(user.getRole())
                .build();
    }

    public List<UserDto> mapUserListToDtoList(List<User> userList) {
        log.debug("mapping userList to dtoList: " + Arrays.toString(userList.toArray()));
        return userList.stream().map(this::mapUserToDto).toList();
    }

    public User mapDtoToUser(UserDto userDto) {
        log.debug("mapping dto to user: " + userDto);
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .lastName(userDto.getLastName())
                .role(userDto.getRole())
                .build();
    }

    public List<User> mapDtoListToUserList(List<UserDto> userDtoList) {
        log.debug("mapping dtoList to userList: " + Arrays.toString(userDtoList.toArray()));
        return userDtoList.stream().map(this::mapDtoToUser).toList();
    }
}
