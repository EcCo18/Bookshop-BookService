package com.example.userservice.services;

import com.example.userservice.model.User;
import com.example.userservice.repos.UserRepository;
import com.example.userservice.services.metrics.UserMetricService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserMetricService userMetricService;
    private final UserRepository userRepository;
    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

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

    public User createUser(User user) {
        User createdUser = userRepository.save(user);
        userMetricService.processCreation(user);
        log.debug("User created: " + user);
        return createdUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            String errorMessage = "couldn't find user: " + username;
            log.warn(errorMessage);
            throw new UsernameNotFoundException(errorMessage);
        } else {
            log.info("user {} fetched from db", username);
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    authorities
            );
        }
    }
}
