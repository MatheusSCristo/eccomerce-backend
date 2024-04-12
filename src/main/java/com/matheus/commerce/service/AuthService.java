package com.matheus.commerce.service;

import com.matheus.commerce.domain.Order;
import com.matheus.commerce.domain.User;
import com.matheus.commerce.dto.order.OrderResponseDto;
import com.matheus.commerce.dto.user.UserAccessResponseDto;
import com.matheus.commerce.dto.user.UserCreateDto;
import com.matheus.commerce.dto.user.UserLoginDto;
import com.matheus.commerce.dto.user.UserResponseDto;
import com.matheus.commerce.exceptions.EmailAlreadyRegisteredException;
import com.matheus.commerce.exceptions.UserNotFoundException;
import com.matheus.commerce.infra.security.AuthenticationResponse;
import com.matheus.commerce.infra.security.JwtService;
import com.matheus.commerce.repository.UserRepository;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

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


    public UserAccessResponseDto register(UserCreateDto userCreateDto) {
        Optional<User> userOptional = userRepository.findByEmail(userCreateDto.email());
        if (userOptional.isPresent()) {
            throw new EmailAlreadyRegisteredException();
        }
        String hashPassword = passwordEncoder.encode(userCreateDto.password());
        User user = new User(userCreateDto, hashPassword);
        userRepository.save(user);
        String token = jwtService.generateToken(user);
        return new UserAccessResponseDto(user, getResponseOrder(user.getOrders()), token);
    }

    public UserAccessResponseDto authenticate(UserLoginDto userLoginDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginDto.email(), userLoginDto.password())
            );
            User user = (userRepository.findByEmail(userLoginDto.email()).orElseThrow());
            String token = jwtService.generateToken(user);
            return new UserAccessResponseDto(user, getResponseOrder(user.getOrders()), token);
        } catch (NoSuchElementException exception) {
            throw new UserNotFoundException();
        }
    }

    public Set<OrderResponseDto> getResponseOrder(Set<Order> orders) {
        Set<OrderResponseDto> orderResponseDtoSet = new HashSet<>();
        for (Order order : orders) {
            orderResponseDtoSet.add(new OrderResponseDto(order));
        }
        return orderResponseDtoSet;
    }
}
