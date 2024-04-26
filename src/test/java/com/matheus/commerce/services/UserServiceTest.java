package com.matheus.commerce.services;

import com.matheus.commerce.domain.User;
import com.matheus.commerce.dto.user.UserCreateDto;
import com.matheus.commerce.dto.user.UserResponseDto;
import com.matheus.commerce.dto.user.UserUpdateDto;
import com.matheus.commerce.enums.Role;
import com.matheus.commerce.infra.exceptions.EmailAlreadyRegisteredException;
import com.matheus.commerce.infra.exceptions.UserNotFoundException;
import com.matheus.commerce.repository.UserRepository;
import com.matheus.commerce.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;




    private UserService userService;

    @BeforeEach
    void setup() {
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    @DisplayName("Should find user by it`s id")
    public void shouldFindUserById() {
        User user = new User();
        Mockito.when(userRepository.findById("123")).thenReturn(Optional.of(user));
        UserResponseDto userResponseDto = userService.findById("123");
        Assertions.assertEquals(user.getId(), userResponseDto.id());
    }

    @Test
    @DisplayName("Should throw UserNotFoundException when on find by id")
    public void shouldThrowUserNotFoundExceptionOnFindUserById() {
        Mockito.when(userRepository.findById("123")).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.findById("123"));
    }

    @Test
    @DisplayName("Should find all users")
    public void shouldFindAllUsers() {
        User user1 = new User();
        User user2 = new User();
        Mockito.when(userRepository.findAll()).thenReturn(new ArrayList<>(List.of(user1, user2)));
        List<UserResponseDto> userResponseDtoList = userService.findAll();
        Assertions.assertEquals(userResponseDtoList.get(0).id(), user1.getId());
        Assertions.assertEquals(userResponseDtoList.get(1).id(), user2.getId());
    }

    @Test
    @DisplayName("Should update user")
    public void shouldUpdateUser() {
        User user = new User(new UserCreateDto("Matheus", "Senas", "20-04-2008", "oldEmail@gmail.com", "12345", "123123123"), "asdasda");
        Mockito.when(userRepository.findByEmail("newEmail@gmail.com")).thenReturn(Optional.empty());
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        UserResponseDto userResponseDto = userService.update(user.getId(), new UserUpdateDto("Felipe", "Melo", "20-04-2004", "newEmail@gmail.com", null, null, null, true));
        Assertions.assertEquals(userResponseDto.id(), user.getId());
        Assertions.assertEquals("Felipe", userResponseDto.name());
        Assertions.assertEquals("Melo", userResponseDto.lastName());
        Assertions.assertEquals("20-04-2004", userResponseDto.birthDate());
        Assertions.assertEquals("newEmail@gmail.com", userResponseDto.email());
        Assertions.assertEquals("123123123", userResponseDto.cpf());
        Assertions.assertEquals(Role.USER, userResponseDto.role());
    }

    @Test
    @DisplayName("Should throw UserNotFoundException on updating user")
    public void shouldThrowUserNotFoundExceptionOnUpdate() {
        Mockito.when(userRepository.findByEmail("newEmail@gmail.com")).thenReturn(Optional.empty());
        Mockito.when(userRepository.findById("123")).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class,() -> userService.update("123", new UserUpdateDto("Felipe", "Melo", "20-04-2004", "newEmail@gmail.com", null, null, null, true)));
    }@Test
    @DisplayName("Should throw EmailAlreadyRegisteredException on updating user")
    public void shouldThrowEmailAlreadyRegisteredExceptionOnUpdate() {
        User user = new User();
        Mockito.when(userRepository.findByEmail("newEmail@gmail.com")).thenReturn(Optional.of(user));
        Assertions.assertThrows(EmailAlreadyRegisteredException.class,() -> userService.update("123", new UserUpdateDto("Felipe", "Melo", "20-04-2004", "newEmail@gmail.com", null, null, null, true)));
    }
    @Test
    @DisplayName("Should delete user")
    public void shouldDelete() {
        Assertions.assertDoesNotThrow(() -> userService.delete("123"));
    }

    @Test
    @DisplayName("Should throw UserNotFoundException on deleting user")
    public void shouldThrowUserNotFoundExceptionOnDelete() {
        Mockito.doThrow(IllegalArgumentException.class).when(userRepository).deleteById(null);
        Assertions.assertThrows(UserNotFoundException.class,() -> userService.delete(null));
    }
}
