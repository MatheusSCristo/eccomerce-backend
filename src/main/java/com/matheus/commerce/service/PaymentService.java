package com.matheus.commerce.service;


import com.matheus.commerce.controller.OrderController;
import com.matheus.commerce.controller.PaymentController;
import com.matheus.commerce.domain.Order;
import com.matheus.commerce.domain.Payment;
import com.matheus.commerce.dto.PaymentDto;
import com.matheus.commerce.dto.PaymentResponseDto;
import com.matheus.commerce.dto.PaymentUpdateDto;
import com.matheus.commerce.enums.PaymentStatus;
import com.matheus.commerce.repository.OrderRepository;
import com.matheus.commerce.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    public void create(PaymentDto paymentDto) {
        Optional<Order> optionalOrder = orderRepository.findById(paymentDto.orderId());
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            Optional<Payment> paymentOptional = paymentRepository.findByOrder(order);
            if (paymentOptional.isPresent()) {
                Payment payment = paymentOptional.get();
                if (payment.getPaymentStatus() != PaymentStatus.refused) {
                    throw new RuntimeException("Cannot create payment for the order as there is already a payment that is not refused");
                }
            }

            Payment payment = new Payment(order);
            order.setPayment(payment);
            paymentRepository.save(payment);
            orderRepository.save(order);
        }

    }

    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    public void update(PaymentUpdateDto paymentUpdateDto) {
        Optional<Payment> optionalPayment = paymentRepository.findById(paymentUpdateDto.id());
        if (optionalPayment.isPresent()) {
            Payment payment = optionalPayment.get();
            payment.setPaymentStatus(paymentUpdateDto.paymentStatus());
            paymentRepository.save(payment);
        }

    }
}
