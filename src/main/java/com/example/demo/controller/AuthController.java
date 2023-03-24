package com.example.demo.controller;

import com.example.demo.model.AuthenticationRequest;
import com.example.demo.model.RegisterRequest;
import com.example.demo.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/auth")
    public ResponseEntity<Object> register(@RequestBody AuthenticationRequest request){
        System.out.println("test");
        return ResponseEntity.ok(authService.authenticate(request));
    }

}