package com.example.userservice.security.user;

import com.example.userservice.model.User;
import com.example.userservice.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        log.info("called user authentication");

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        Optional<User> user = userService.getUserByUsername(username);

        if(user.isEmpty()) {
            throw new BadCredentialsException("couldn't find username");
        }
        if(passwordEncoder.matches(password, user.get().getPassword())) {
            log.info("successfully logged in user");
            return new UsernamePasswordAuthenticationToken(username, password, null);
        } else {
            throw new BadCredentialsException("password mismatch");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
