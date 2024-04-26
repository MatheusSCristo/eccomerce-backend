package com.matheus.commerce.dto.billingDetails;

import jakarta.validation.constraints.NotBlank;

public record   BillingDetailsDto(@NotBlank String name, @NotBlank String lastName, @NotBlank String email, @NotBlank String phone,
                                @NotBlank String cep, @NotBlank String city, @NotBlank String street, @NotBlank String neighborhood) {
}
