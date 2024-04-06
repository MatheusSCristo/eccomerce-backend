package com.matheus.commerce.dto.payment;

import com.matheus.commerce.enums.PaymentStatus;

public record PaymentDto(String orderId, String cardNumber, PaymentStatus paymentStatus) {
}
