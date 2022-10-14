package com.example.userservice.services;

import org.springframework.stereotype.Service;

@Service
public class UserUtilService {

    public String createUserName(String name, String surname) {
        return name + "." + surname;
    }
}
