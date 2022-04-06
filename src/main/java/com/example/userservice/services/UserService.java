package com.example.userservice.services;

import com.example.userservice.model.User;
import com.example.userservice.repos.UserRepository;
import com.example.userservice.services.metrics.UserMetricService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<User> getUserById(int userId) {
        log.debug("getting user with id: " + userId);
        Optional<User> foundUser = userRepository.findById(userId);

        if (foundUser.isPresent()) {
            log.debug("found user: " + foundUser.get());
        } else {
            log.debug("user with id: " + userId + "could not be found");
        }
        return foundUser;
    }

    public User createUser(User user) {
        User createdUser = userRepository.save(user);
        userMetricService.processCreation(user);
        log.debug("User created: " + user);
        return createdUser;
    }
}
