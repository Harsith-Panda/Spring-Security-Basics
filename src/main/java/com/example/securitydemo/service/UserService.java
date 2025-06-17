package com.example.securitydemo.service;

import com.example.securitydemo.model.User;
import com.example.securitydemo.repo.UserRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    private UserRepo repo;
    UserService(UserRepo rp) {
        this.repo = rp;
    }

    public User regUser(User us) {
        us.setPassword(encoder.encode(us.getPassword()));
        return repo.save(us);
    }
}
