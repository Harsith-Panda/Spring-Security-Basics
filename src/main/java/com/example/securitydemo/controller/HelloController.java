package com.example.securitydemo.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/")
    public String getHello(HttpServletRequest request) {
        return "Hello " + request.getSession().getId();
    }

    @PostMapping("/")
    public String getEvil() {
        return "Welcome to the evil side!";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String chumma(HttpServletRequest req) {
        return "Welcome Admin!" + req.getSession().getId();
    }
}
