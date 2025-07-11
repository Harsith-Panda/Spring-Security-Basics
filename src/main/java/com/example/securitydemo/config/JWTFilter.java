package com.example.securitydemo.config;

import com.example.securitydemo.service.JWTService;
import com.example.securitydemo.service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private JWTService jwtService;
    private ApplicationContext context;

    JWTFilter(JWTService js, ApplicationContext con) {
        this.jwtService = js;
        this.context = con;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Bearer <Token>
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username = jwtService.extractUserName(token);
        }

        if (username != null) {

            UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(username);

            System.out.println("User details: " + userDetails.getUsername());
            userDetails.getAuthorities().forEach(auth -> System.out.println("Authority: " + auth.getAuthority()));

            if (jwtService.validate(userDetails, token)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        System.out.println("Before filterChain.doFilter:");
        System.out.println("Auth: " + SecurityContextHolder.getContext().getAuthentication());
        filterChain.doFilter(request, response);
        System.out.println("After:");
        System.out.println("Auth: " + SecurityContextHolder.getContext().getAuthentication());
    }
}
