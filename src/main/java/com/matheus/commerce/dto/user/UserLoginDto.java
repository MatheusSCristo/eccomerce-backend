package com.matheus.commerce.dto.user;


import javax.validation.constraints.NotBlank;

public record UserLoginDto(@NotBlank String email, @NotBlank String password) {
}
