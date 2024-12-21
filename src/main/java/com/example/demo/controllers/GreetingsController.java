package com.example.demo.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class GreetingsController {
    @GetMapping
    public ResponseEntity<?> Hello() {
        Map<String, Object> response = new HashMap<>();

        response.put("message", "Hello World!");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/hello/{name}")
    public ResponseEntity<?> Hello(@PathVariable String name) {
        return ResponseEntity.ok("Hello, " + name.trim());
    }
}
