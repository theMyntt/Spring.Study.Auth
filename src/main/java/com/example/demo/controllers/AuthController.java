package com.example.demo.controllers;

import com.example.demo.dtos.LoginResponseDTO;
import com.example.demo.dtos.RegisterDTO;
import com.example.demo.dtos.UserDTO;
import com.example.demo.entities.UserEntity;
import com.example.demo.repositories.UserRepository;
import com.example.demo.security.TokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login/")
    public ResponseEntity<?> login(@RequestBody UserDTO dto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((UserEntity) auth.getPrincipal());

        return ResponseEntity.ok(LoginResponseDTO.builder().token(token).build());
    }

    @PostMapping("/register/")
    public ResponseEntity<?> register(@RequestBody RegisterDTO dto) {
        if (userRepository.findByEmail(dto.getEmail()) != null)
            ResponseEntity.badRequest().build();

        UserEntity user = new UserEntity();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setName(dto.getName());
        user.setRole(dto.getRole());

        userRepository.save(user);

        return ResponseEntity.status(201).body(null);
    }
}
