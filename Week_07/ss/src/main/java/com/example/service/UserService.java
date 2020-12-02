package com.example.service;

import com.example.entity.User;

import java.util.List;

public interface UserService {

    Long addUser(User user);

    List<User> list();
}
