package com.example.service.impl;

import com.example.dao.UserMapping;
import com.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements userService{
    @Autowired
    UserMapping userMapping;

    @Override
    public void addUser(User u) {
        userMapping.addUser(u);
    }

    @Override
    public User getUser(Integer userId, String name) {
        return userMapping.getUser(userId,name);
    }
}
