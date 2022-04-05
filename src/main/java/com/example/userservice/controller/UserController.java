package com.example.userservice.controller;

import com.example.userservice.mapper.UserMapper;
import com.example.userservice.model.User;
import com.example.userservice.model.dto.UserDto;
import com.example.userservice.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping()
    public List<UserDto> getAllUser() {
        log.info("Received GET for all users");
        return userMapper.mapUserListToDtoList(userService.getAllUser());
    }

    @PostMapping()
    public User postUser(@RequestBody UserDto postedUser) {
        log.info("Received POST for user");

        return null;
    }
 }
