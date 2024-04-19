package com.matheus.commerce.services;


import com.matheus.commerce.domain.User;
import com.matheus.commerce.infra.exceptions.UserNotFoundException;
import com.matheus.commerce.repository.UserRepository;
import com.matheus.commerce.service.UserDetailsImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@SpringBootTest
public class UserDetailsImpTest {

    @Mock
    private UserRepository userRepository;

    private UserDetailsImp userDetailsImp;

    @BeforeEach
    void setup(){
        userDetailsImp= new UserDetailsImp(userRepository);
    }

    @Test
    @DisplayName("Should load user by username")
    public void shouldLoadUserByUsername(){
        User user= new User();
        Mockito.when(userRepository.findByEmail("matheus@gmail.com")).thenReturn(Optional.of(user));
        UserDetails userDetails=userDetailsImp.loadUserByUsername("matheus@gmail.com");
        Assertions.assertEquals(userDetails.getUsername(),user.getUsername());
        Assertions.assertEquals(userDetails.getPassword(),user.getPassword());
    }
    @Test
    @DisplayName("Should throw UserNotFoundException when user not found")
    public void shouldThrowUserNotFoundExceptionWhenUserNotFound(){
        Mockito.when(userRepository.findByEmail("emailthatdoesntexists@gmail.com")).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class,()->userDetailsImp.loadUserByUsername("emailthatdoesntexists@gmail.com"));
    }

}
