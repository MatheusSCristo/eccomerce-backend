package com.matheus.commerce.services;

import com.matheus.commerce.domain.User;
import com.matheus.commerce.dto.user.UserAccessResponseDto;
import com.matheus.commerce.dto.user.UserCreateDto;
import com.matheus.commerce.dto.user.UserLoginDto;
import com.matheus.commerce.enums.Role;
import com.matheus.commerce.infra.exceptions.AuthenticationException;
import com.matheus.commerce.infra.exceptions.CredentialsError;
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
    private UserCreateDto userCreated = new UserCreateDto(name, lastName, age, email, password, role, cpf);
    private UserLoginDto userLogged=new UserLoginDto(email,password);


    @Test
    public void ShouldRegisterNewUser() {
        UserAccessResponseDto userAccessResponseDto = authService.register(userCreated);
        Assertions.assertEquals(userAccessResponseDto.name(), name);
        Assertions.assertEquals(userAccessResponseDto.lastName(), lastName);
        Assertions.assertEquals(userAccessResponseDto.age(), age);
        Assertions.assertEquals(userAccessResponseDto.email(), email);
        Assertions.assertEquals(userAccessResponseDto.role(), role);
        Assertions.assertEquals(userAccessResponseDto.cpf(), cpf);
        authService.delete(userAccessResponseDto.id());
    }

    @Test
    public void ShouldThrowWhenEmailAlreadyRegistered() {
        UserAccessResponseDto userAccessResponseDto = authService.register(userCreated);
        Assertions.assertThrows(EmailAlreadyRegisteredException.class, () -> authService.register(userCreated));
        authService.delete(userAccessResponseDto.id());

    }

    @Test
    public void ShouldAuthenticatedUser(){
        authService.register(userCreated);
        UserAccessResponseDto userAccessResponseDto = authService.authenticate(userLogged);
        Assertions.assertEquals(userAccessResponseDto.name(), name);
        Assertions.assertEquals(userAccessResponseDto.lastName(), lastName);
        Assertions.assertEquals(userAccessResponseDto.age(), age);
        Assertions.assertEquals(userAccessResponseDto.email(), email);
        Assertions.assertEquals(userAccessResponseDto.role(), role);
        Assertions.assertEquals(userAccessResponseDto.cpf(), cpf);
        Assertions.assertNotNull(userAccessResponseDto.accessToken());
        authService.delete(userAccessResponseDto.id());

    }

    @Test
    public void ShouldThrowWhenEmailNotRegistered(){
        Assertions.assertThrows(AuthenticationException.class,()->authService.authenticate(userLogged));
    }

    @Test
    public void ShouldThrowWhenPasswordDoesntMatch(){
        UserAccessResponseDto userAccessResponseDto=authService.register(userCreated);
        Assertions.assertThrows(CredentialsError.class,()->authService.authenticate(new UserLoginDto(email,"123")));
        authService.delete(userAccessResponseDto.id());
    }



}
