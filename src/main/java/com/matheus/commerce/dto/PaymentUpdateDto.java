package com.matheus.commerce.dto;

import com.matheus.commerce.enums.PaymentStatus;

public record PaymentUpdateDto(PaymentStatus paymentStatus,String id) {
}
