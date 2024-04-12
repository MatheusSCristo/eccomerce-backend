package com.matheus.commerce.service;


import com.matheus.commerce.domain.Order;
import com.matheus.commerce.domain.Payment;
import com.matheus.commerce.dto.order.OrderResponseDto;
import com.matheus.commerce.dto.payment.PaymentDto;
import com.matheus.commerce.dto.payment.PaymentUpdateDto;
import com.matheus.commerce.enums.PaymentStatus;
import com.matheus.commerce.infra.exceptions.OrderNotFoundException;
import com.matheus.commerce.infra.exceptions.PaymentNotFoundException;
import com.matheus.commerce.infra.exceptions.PaymentStillProcessingException;
import com.matheus.commerce.repository.OrderRepository;
import com.matheus.commerce.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                    throw new PaymentStillProcessingException();
                }
            }
            Payment payment = new Payment(order);
            order.setPayment(payment);
            paymentRepository.save(payment);
            orderRepository.save(order);
        } else {
            throw new OrderNotFoundException();
        }

    }

    public OrderResponseDto update(PaymentUpdateDto paymentUpdateDto) {
        Optional<Payment> optionalPayment = paymentRepository.findById(paymentUpdateDto.id());
        if (optionalPayment.isPresent()) {
            Payment payment = optionalPayment.get();
            Optional<Order> orderOptional = orderRepository.findById(payment.getOrder().getId());
            payment.setPaymentStatus(paymentUpdateDto.paymentStatus());
            paymentRepository.save(payment);
            return new OrderResponseDto(orderOptional.get());
        } else {
            throw new PaymentNotFoundException();
        }

    }
}
