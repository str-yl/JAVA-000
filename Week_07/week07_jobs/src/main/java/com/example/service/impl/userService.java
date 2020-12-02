package com.example.service.impl;

import com.example.entity.User;

public interface userService {
    void addUser(User u);

    User getUser(Integer userId,String name);
}
