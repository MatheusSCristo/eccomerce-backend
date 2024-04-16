package com.matheus.commerce.services;

import com.matheus.commerce.domain.User;
import com.matheus.commerce.dto.user.UserAccessResponseDto;
import com.matheus.commerce.dto.user.UserCreateDto;
import com.matheus.commerce.dto.user.UserLoginDto;
import com.matheus.commerce.enums.Role;
import com.matheus.commerce.infra.exceptions.EmailAlreadyRegisteredException;
import com.matheus.commerce.service.AuthService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    private String name = "Matheus";
    private String lastName = "Senas";
    private int age = 19;
    private String email = "matheus@gmail.com";
    private String password = "123456";
    private Role role = Role.USER;
    private String cpf = "07233484902";

    @Test
    public void shouldRegisterNewUser() {

        UserCreateDto userCreateDto = new UserCreateDto(name, lastName, age, email, password, role, cpf);
        UserAccessResponseDto userAccessResponseDto = authService.register(userCreateDto);
        Assertions.assertEquals(userAccessResponseDto.name(), name);
        Assertions.assertEquals(userAccessResponseDto.lastName(), lastName);
        Assertions.assertEquals(userAccessResponseDto.age(), age);
        Assertions.assertEquals(userAccessResponseDto.email(), email);
        Assertions.assertEquals(userAccessResponseDto.role(), role);
        Assertions.assertEquals(userAccessResponseDto.cpf(), cpf);
    }

    @Test
    public void shouldThrowWhenEmailAlreadyRegistered() {
        UserCreateDto user = new UserCreateDto(name, lastName, age, email, password, role, cpf);
        UserAccessResponseDto userAccessResponseDto = authService.register(user);
        Assertions.assertThrows(EmailAlreadyRegisteredException.class, () -> authService.register(user));
    }

    @Test
    public void shouldAuthenticatedUser(){
        UserLoginDto userLoginDto=new UserLoginDto(email,password);
        UserCreateDto userCreateDto = new UserCreateDto(name, lastName, age, email, password, role, cpf);
        authService.register(userCreateDto);
        UserAccessResponseDto userAccessResponseDto = authService.authenticate(userLoginDto);
        Assertions.assertEquals(userAccessResponseDto.name(), name);
        Assertions.assertEquals(userAccessResponseDto.lastName(), lastName);
        Assertions.assertEquals(userAccessResponseDto.age(), age);
        Assertions.assertEquals(userAccessResponseDto.email(), email);
        Assertions.assertEquals(userAccessResponseDto.role(), role);
        Assertions.assertEquals(userAccessResponseDto.cpf(), cpf);
        Assertions.assertNotNull(userAccessResponseDto.accessToken());
    }


}
