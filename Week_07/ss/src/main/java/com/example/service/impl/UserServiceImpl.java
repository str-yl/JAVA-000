package com.example.service.impl;

import com.example.entity.User;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public Long addUser(User user) {
        return userRepository.addUser(user);
    }

    @Override
    public List<User> list() {
        return userRepository.list();
    }
}
