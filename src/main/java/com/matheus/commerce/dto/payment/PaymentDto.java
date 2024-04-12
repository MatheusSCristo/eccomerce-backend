package com.matheus.commerce.dto.payment;

import jakarta.validation.constraints.NotBlank;

public record PaymentDto(@NotBlank String orderId) {
}
