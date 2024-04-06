package com.matheus.commerce.dto.user;

import com.matheus.commerce.domain.User;
import com.matheus.commerce.enums.Role;

public record UserResponseDto(String id, String name, String lastName, Integer age, String email, Role role, String cpf) {
    public UserResponseDto(User user) {
        this(user.getId(), user.getName(), user.getLastName(), user.getAge(), user.getEmail(), user.getRole(), user.getCpf());
    }
}
