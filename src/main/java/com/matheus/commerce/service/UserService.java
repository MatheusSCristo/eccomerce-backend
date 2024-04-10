package com.matheus.commerce.service;

import com.matheus.commerce.domain.User;
import com.matheus.commerce.dto.user.UserCreateDto;
import com.matheus.commerce.dto.user.UserResponseDto;
import com.matheus.commerce.dto.user.UserUpdateDto;
import com.matheus.commerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserResponseDto findById(String id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return new UserResponseDto(user);
        }
        return null;
    }

    public List<UserResponseDto> findAll() {
        List<User> userList = userRepository.findAll();
        List<UserResponseDto> userResponseList = new ArrayList<>();
        for (User user : userList) {
            userResponseList.add(new UserResponseDto(user));
        }
        return userResponseList;
    }

    public UserResponseDto update(String id, UserUpdateDto userUpdateDto) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            User newUser = updateUser(user, userUpdateDto);
            userRepository.save(newUser);
            return new UserResponseDto(newUser);
        }
        return null;
    }

    public void delete(String id) {
        userRepository.deleteById(id);
    }

    private User updateUser(User user, UserUpdateDto userUpdateDto) {
        user.setName(userUpdateDto.name() != null ? userUpdateDto.name() : user.getName());
        user.setLastName(userUpdateDto.lastName() != null ? userUpdateDto.lastName() : user.getLastName());
        user.setAge(userUpdateDto.age() != null ? userUpdateDto.age() : user.getAge());
        user.setCpf(userUpdateDto.cpf() != null ? userUpdateDto.cpf() : user.getCpf());
        user.setPassword(userUpdateDto.password() != null ? userUpdateDto.password() : user.getPassword());
        user.setEmail(userUpdateDto.email() != null ? userUpdateDto.email() : user.getEmail());
        user.setVerifiedEmail(userUpdateDto.verifiedEmail() != null ? userUpdateDto.verifiedEmail() : user.isVerifiedEmail());
        user.setRole(userUpdateDto.role() != null ? userUpdateDto.role() : user.getRole());
        user.setUpdatedAt(LocalDate.now());
        return user;
    }

}
