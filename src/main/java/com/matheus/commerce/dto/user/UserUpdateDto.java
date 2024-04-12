package com.matheus.commerce.dto.user;

import com.matheus.commerce.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record UserUpdateDto(@NotBlank String name, @NotBlank String lastName, @NotNull Integer age, @NotBlank String email,
                            @NotBlank String password, @NotNull Role role, @NotBlank String cpf,
                            @NotBlank Boolean verifiedEmail) {
}
