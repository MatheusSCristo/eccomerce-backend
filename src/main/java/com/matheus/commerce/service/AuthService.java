package com.matheus.commerce.service;

import com.matheus.commerce.domain.User;
import com.matheus.commerce.dto.user.UserCreateDto;
import com.matheus.commerce.dto.user.UserLoginDto;
import com.matheus.commerce.infra.security.AuthenticationResponse;
import com.matheus.commerce.infra.security.JwtService;
import com.matheus.commerce.repository.UserRepository;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;


    public AuthenticationResponse register(UserCreateDto userCreateDto) {
        String hashPassword = passwordEncoder.encode(userCreateDto.password());
        User user = new User(userCreateDto, hashPassword);
        userRepository.save(user);
        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse authenticate(UserLoginDto userLoginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDto.email(), userLoginDto.password())
        );
        User user=(userRepository.findByEmail(userLoginDto.email()).orElseThrow());
        String token=jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }
}
