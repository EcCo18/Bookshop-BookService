package com.example.userservice.services;

import com.example.userservice.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Slf4j
public class UserService {

    private final User[] user = new User[] {
            new User(1, "Joe", "Doe"),
            new User(2, "Max", "Mustermann"),
            new User(3, "Jan", "Nobody")
    };

    public User[] getAllUser() {
        log.debug("get all users called");
        User[] user = new User[]{
                new User(1, "Joe", "Doe"),
                new User(2, "Max", "Mustermann"),
                new User(3, "Jan", "Nobody")
        };
        log.debug("returning " + Arrays.toString(user));
        return user;
    }
}
