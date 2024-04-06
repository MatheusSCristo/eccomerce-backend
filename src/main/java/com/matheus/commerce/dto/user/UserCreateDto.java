package com.matheus.commerce.dto.user;

import com.matheus.commerce.enums.Role;

public record UserCreateDto(String name, String lastName, Integer age, String email, String password, Role role,String cpf) {
}
