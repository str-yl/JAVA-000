package com.example.controller;


import com.example.entity.User;
import com.example.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServiceImpl userServiceImpl;

    @GetMapping("/addUser")
    public String addUser(User u) {
        userServiceImpl.addUser(u);
        return "success";
    }
    @GetMapping("/getUser")
    public User getUser(Integer userId, String name) {
        return userServiceImpl.getUser(userId, name);
    }
}
