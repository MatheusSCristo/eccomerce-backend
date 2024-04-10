package com.matheus.commerce.controller;

import com.matheus.commerce.domain.User;
import com.matheus.commerce.dto.user.UserCreateDto;
import com.matheus.commerce.dto.user.UserLoginDto;
import com.matheus.commerce.infra.security.AuthenticationResponse;
import com.matheus.commerce.repository.UserRepository;
import com.matheus.commerce.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid UserCreateDto userCreateDto){
        return ResponseEntity.ok(authService.register(userCreateDto));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> getUser(@RequestBody @Valid UserLoginDto userLoginDto){
        return ResponseEntity.ok(authService.authenticate(userLoginDto));
    }



}

