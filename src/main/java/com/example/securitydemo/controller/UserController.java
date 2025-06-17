package com.example.securitydemo.controller;

import com.example.securitydemo.model.User;
import com.example.securitydemo.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private UserService service;

    UserController(UserService serv) {
        this.service = serv;
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return service.regUser(user);
    }

}
