package com.matheus.commerce.dto.payment;

import com.matheus.commerce.enums.PaymentStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PaymentUpdateDto(@NotNull PaymentStatus paymentStatus,@NotBlank String id) {
}
