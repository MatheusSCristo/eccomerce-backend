package com.matheus.commerce.controller;

import com.matheus.commerce.dto.user.UserAccessResponseDto;
import com.matheus.commerce.dto.user.UserCreateDto;
import com.matheus.commerce.dto.user.UserLoginDto;
import com.matheus.commerce.repository.UserRepository;
import com.matheus.commerce.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("auth")
@AllArgsConstructor
public class AuthController {

    @Autowired
    private AuthService authService;
k

    @PostMapping(value ="/register",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserAccessResponseDto> register(@RequestBody @Valid UserCreateDto userCreateDto) {
        return ResponseEntity.ok(authService.register(userCreateDto));
    }

    @PostMapping("/login")
    public ResponseEntity<UserAccessResponseDto> getUser(@RequestBody @Valid UserLoginDto userLoginDto) {
        return ResponseEntity.ok(authService.authenticate(userLoginDto));
    }


}

