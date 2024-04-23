package com.matheus.commerce.dto.user;

import com.matheus.commerce.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;


public record UserUpdateDto(String name, String lastName, String birthDate, String email,
                            String password, Role role, String cpf,
                            Boolean verifiedEmail) {
}
