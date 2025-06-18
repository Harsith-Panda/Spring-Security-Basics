package com.example.securitydemo.service;

import com.example.securitydemo.model.User;
import com.example.securitydemo.repo.UserRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    private UserRepo repo;
    private AuthenticationManager authenticationManager;
    private JWTService jwtService;
    UserService(UserRepo rp, AuthenticationManager am, JWTService jw) {
        this.repo = rp;
        this.authenticationManager = am;
        this.jwtService = jw;
    }

    public User regUser(User us) {
        us.setPassword(encoder.encode(us.getPassword()));
        return repo.save(us);
    }

    public String login(User us) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(us.getUsername(), us.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(us.getUsername());
        }
        return "Failure";
    }
}
