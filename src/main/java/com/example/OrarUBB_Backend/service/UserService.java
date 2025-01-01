package com.example.OrarUBB_Backend.service;

import org.springframework.stereotype.Service;

import com.example.OrarUBB_Backend.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
