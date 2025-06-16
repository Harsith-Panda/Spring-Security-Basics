package com.example.securitydemo.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/")
    public String getHello(HttpServletRequest request) {
        return "Hello " + request.getSession().getId();
    }

    @PostMapping("/")
    public String getEvil() {
        return "Welcome to the evil side!";
    }
}
