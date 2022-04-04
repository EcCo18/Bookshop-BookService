package com.example.userservice.services;

import com.example.userservice.model.User;
import com.example.userservice.services.metrics.UserMetricService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserMetricService userMetricService;

    private final User[] user = new User[] {
            new User(1, "Joe", "Doe"),
            new User(2, "Max", "Mustermann"),
            new User(3, "Jan", "Nobody")
    };

    public User[] getAllUser() {
        userMetricService.processCreation(new User());
        User[] user = new User[]{
                new User(1, "Joe", "Doe"),
                new User(2, "Max", "Mustermann"),
                new User(3, "Jan", "Nobody")
        };
        log.debug("getAllUsers called returning: " + Arrays.toString(user));
        return user;
    }
}
