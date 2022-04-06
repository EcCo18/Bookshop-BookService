package com.example.userservice.controller;

import com.example.userservice.services.mapper.UserMapper;
import com.example.userservice.model.User;
import com.example.userservice.model.dto.UserDto;
import com.example.userservice.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping()
    public ResponseEntity<List<UserDto>> getAllUser() {
        log.info("Received GET for all users");
        return ResponseEntity.ok(userMapper.mapUserListToDtoList(userService.getAllUser()));
    }

    // ToDo finish
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("userId") int userId) {
        log.info("received GET for user with id: " + userId);
        return null;
    }

    @PostMapping()
    public ResponseEntity<UserDto> postUser(@Valid @RequestBody UserDto postedUser) {
        log.info("Received POST for user");
        log.debug(postedUser.toString());
        User createdUser = userService.createUser(userMapper.mapDtoToUser(postedUser));
        return ResponseEntity.ok(userMapper.mapUserToDto(createdUser));
    }
 }
