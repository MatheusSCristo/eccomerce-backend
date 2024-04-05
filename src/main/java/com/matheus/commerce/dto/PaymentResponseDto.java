package com.matheus.commerce.dto;

import com.matheus.commerce.domain.Payment;
import com.matheus.commerce.enums.PaymentStatus;
import com.matheus.commerce.service.PaymentService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResponseDto {
    private String id;
    private PaymentStatus paymentStatus;
    private String orderId;

    public PaymentResponseDto(Payment payment) {
        this.id= payment.getId();
        this.paymentStatus=payment.getPaymentStatus();
        this.orderId=payment.getOrder().getId();
    }
}
