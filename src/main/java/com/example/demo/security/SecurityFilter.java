package com.example.demo.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.repositories.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var token = recoverToken(request);

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        var email = tokenService.validateToken(token);
        var userdetails = userRepository.findByEmail(email);

        var authencitation = new UsernamePasswordAuthenticationToken(userdetails, null, userdetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authencitation);
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var auth = request.getHeader("Authorization");

        if (auth == null)
            return auth;

        return auth.replace("Bearer ", "");
    }
}
