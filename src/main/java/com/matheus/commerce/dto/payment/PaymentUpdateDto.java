package com.matheus.commerce.dto.payment;

import com.matheus.commerce.enums.PaymentStatus;

public record PaymentUpdateDto(PaymentStatus paymentStatus,String id) {
}
