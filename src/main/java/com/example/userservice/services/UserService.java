package com.example.userservice.services;

import com.example.userservice.model.User;
import com.example.userservice.repos.UserRepository;
import com.example.userservice.services.metrics.UserMetricService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserMetricService userMetricService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUser() {
        log.debug("get all users called");
        List<User> users = userRepository.findAll();
        userMetricService.processReceived();
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

    public Optional<User> getUserByUsername(String username) {
        log.debug("getting user with username: " + username);
        Optional<User> foundUser = userRepository.findUserByUsername(username);

        if (foundUser.isPresent()) {
            log.debug("found user: " + foundUser.get());
        } else {
            log.warn("user with username: " + username + "could not be found");
        }
        return foundUser;
    }

    public User createUser(User user) {
        user.setHashedPassword(passwordEncoder.encode(user.getHashedPassword()));
        User createdUser = userRepository.save(user);
        userMetricService.processCreation(user);
        log.debug("User created: " + user);
        return createdUser;
    }

    public User deleteUserById(int userId) throws NoSuchElementException {
        User user = userRepository.findById(userId).orElseThrow();
        userRepository.delete(user);

        return user;
    }
}
