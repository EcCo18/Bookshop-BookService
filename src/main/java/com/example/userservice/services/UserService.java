package com.example.userservice.services;

import com.example.userservice.model.User;
import com.example.userservice.repos.UserRepository;
import com.example.userservice.services.metrics.UserMetricService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserMetricService userMetricService;
    private final UserRepository userRepository;

    public List<User> getAllUser() {
        log.debug("get all users called");
        List<User> users = userRepository.findAll();
        userMetricService.processReceived();
        // log.debug("getAllUsers called returning: " + Arrays.toString(users.toArray()));
        return users;
    }

    public User createUser(User user) {
        User createdUser = userRepository.save(user);
        userMetricService.processCreation(user);
        log.debug("User created: " + user);
        return createdUser;
    }
}
