package com.example.demo.service;

import com.example.demo.config.JwtService;
import com.example.demo.dao.UserRepository;
import com.example.demo.helper.ResponseHandler;
import com.example.demo.model.*;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authmanager;

    public AuthService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authmanager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authmanager = authmanager;
    }


    public Object register(RegisterRequest request) {
        if (repository.existsByName(request.getName())) {
            if (request.getName().equals(repository.findByName(request.getName()).get().getName())) {
                return "naam bestaat al";
            }
        }

            User user = new User();
        user.setname(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        repository.save(user);
        var token = jwtService.generateJWTToken(user);
        return ResponseHandler.generateTokenResponse("account succesvol aangemaakt", HttpStatus.OK, token, user.getName(), user.getRole());
    }

    public Object authenticate(AuthenticationRequest request) {
        authmanager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getName(), request.getPassword()));
        var user = repository.findByName(request.getName()).orElseThrow();
        var token = jwtService.generateJWTToken(user);
        return ResponseHandler.generateTokenResponse("login successful", HttpStatus.OK, token, user.getName(), user.getRole());
    }
}
