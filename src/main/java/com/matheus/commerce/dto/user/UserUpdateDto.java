package com.matheus.commerce.dto.user;

import com.matheus.commerce.enums.Role;

public record UserUpdateDto(String name, String lastName, Integer age, String email, String password, Role role, String cpf,Boolean verifiedEmail) {
}
