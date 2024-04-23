package com.matheus.commerce.dto.user;

import com.matheus.commerce.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Date;


public record UserCreateDto(@NotBlank (message = "name") String name,
                            @NotBlank String lastName,
                            @NotNull String birthDate,
                            @NotBlank @Email String email,
                            @NotBlank String password,
                            @NotBlank @CPF String cpf
) {
}
