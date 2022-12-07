package com.example.userservice.controller;

import com.example.userservice.model.User;
import com.example.userservice.model.dto.UserDto;
import com.example.userservice.services.UserService;
import com.example.userservice.services.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
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

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("userId") int userId) {
        log.info("received GET for user with id: " + userId);
        return userService.getUserById(userId)
                .map(user -> ResponseEntity.ok(userMapper.mapUserToDto(user)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ToDo
    @DeleteMapping("/{userId}")
    public ResponseEntity<UserDto> deleteUserById(@PathVariable("userId") int userId) {
        log.info("received DELETE for user with id: " + userId);

        return null;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> postUser(@Valid @RequestBody UserDto postedUser) {
        log.info("Received POST for user");
        log.debug(postedUser.toString());
        User createdUser = userService.createUser(userMapper.mapDtoToUser(postedUser));
        return ResponseEntity.ok(userMapper.mapUserToDto(createdUser));
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser() {
        return ResponseEntity.ok("successfully logged in user " +
                SecurityContextHolder.getContext().getAuthentication().getName());
    }

    //@PostMapping("/refreshToken")
    //public ResponseEntity<String>
 }
