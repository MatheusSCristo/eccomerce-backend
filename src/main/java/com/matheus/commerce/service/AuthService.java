package com.matheus.commerce.service;

import com.matheus.commerce.domain.Order;
import com.matheus.commerce.domain.User;
import com.matheus.commerce.dto.order.OrderResponseDto;
import com.matheus.commerce.dto.user.UserAccessResponseDto;
import com.matheus.commerce.dto.user.UserCreateDto;
import com.matheus.commerce.dto.user.UserLoginDto;
import com.matheus.commerce.infra.exceptions.AuthenticationException;
import com.matheus.commerce.infra.exceptions.CredentialsError;
import com.matheus.commerce.infra.exceptions.EmailAlreadyRegisteredException;
import com.matheus.commerce.infra.exceptions.UserNotFoundException;
import com.matheus.commerce.infra.security.JwtService;
import com.matheus.commerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
        validateFields(userCreateDto);
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

    private void validateFields(UserCreateDto userCreateDto) {
        userCreateDto.email();
    }

    public UserAccessResponseDto authenticate(UserLoginDto userLoginDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginDto.email(), userLoginDto.password())
            );
            User user = (userRepository.findByEmail(userLoginDto.email()).orElseThrow(UserNotFoundException::new));
            String token = jwtService.generateToken(user);
            return new UserAccessResponseDto(user, getResponseOrder(user.getOrders()), token);
        } catch (InternalAuthenticationServiceException exception) {
            throw new AuthenticationException("Error on user authentication");
        } catch (BadCredentialsException exception) {
            throw new CredentialsError();
        }
    }

    public Set<OrderResponseDto> getResponseOrder(Set<Order> orders) {
        Set<OrderResponseDto> orderResponseDtoSet = new HashSet<>();
        if (!orders.isEmpty())
            for (Order order : orders) {
                orderResponseDtoSet.add(new OrderResponseDto(order));
            }
        return orderResponseDtoSet;
    }

    public void delete(String id) {
        userRepository.deleteById(id);
    }
}
