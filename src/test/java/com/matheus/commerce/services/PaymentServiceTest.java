package com.matheus.commerce.services;

import com.matheus.commerce.domain.Order;
import com.matheus.commerce.domain.Payment;
import com.matheus.commerce.dto.payment.PaymentDto;
import com.matheus.commerce.dto.payment.PaymentUpdateDto;
import com.matheus.commerce.enums.PaymentStatus;
import com.matheus.commerce.infra.exceptions.OrderNotFoundException;
import com.matheus.commerce.infra.exceptions.PaymentNotFoundException;
import com.matheus.commerce.infra.exceptions.PaymentStillProcessingException;
import com.matheus.commerce.repository.OrderRepository;
import com.matheus.commerce.repository.PaymentRepository;
import com.matheus.commerce.service.PaymentService;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class PaymentServiceTest {

    @Mock
    PaymentRepository paymentRepository;
    @Mock
    OrderRepository orderRepository;

    PaymentService paymentService;

    @BeforeEach
    void setup() {
        paymentService = new PaymentService(paymentRepository, orderRepository);
    }

    @Test
    @DisplayName("Should create an payment")
    public void shouldCreatePayment() {
        Order order = new Order();
        PaymentDto paymentDto = new PaymentDto(order.getId());
        Payment payment = new Payment();
        payment.setPaymentStatus(PaymentStatus.refused);
        Mockito.when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        Mockito.when(paymentRepository.findByOrder(order)).thenReturn(Optional.of(payment));
        Payment newPayment = paymentService.create(paymentDto);
        Assertions.assertEquals(newPayment.getOrder().getId(), order.getId());
        Assertions.assertEquals(newPayment.getPaymentStatus(), PaymentStatus.pending);
    }

    @Test
    @DisplayName("Should update payment")
    public void shouldUpdatePayment() {
        Order order = new Order();
        Payment payment = new Payment(order);
        PaymentUpdateDto paymentUpdateDto = new PaymentUpdateDto(PaymentStatus.authorized, payment.getId());
        Mockito.when(paymentRepository.findById(payment.getId())).thenReturn(Optional.of(payment));
        Mockito.when(orderRepository.findById(payment.getOrder().getId())).thenReturn(Optional.of(order));
        Payment returnPayment = paymentService.update(paymentUpdateDto);
        Assertions.assertEquals(returnPayment.getPaymentStatus(), PaymentStatus.authorized);
    }

    @Test
    @DisplayName("Should throw PaymentStillProcessingException on create")
    public void shouldThrowPaymentStillProcessingExceptionOnCreate() {
        Order order = new Order();
        PaymentDto paymentDto = new PaymentDto(order.getId());
        Payment payment = new Payment();
        Mockito.when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        Mockito.when(paymentRepository.findByOrder(order)).thenReturn(Optional.of(payment));
        Assertions.assertThrows(PaymentStillProcessingException.class, () -> paymentService.create(paymentDto));
    }

    @Test
    @DisplayName("Should throw OrderNotFoundException on create")
    public void shouldThrowOrderNotFoundExceptionOnCreate() {
        Order order = new Order();
        PaymentDto paymentDto = new PaymentDto(order.getId());
        Payment payment = new Payment();
        payment.setPaymentStatus(PaymentStatus.refused);
        Mockito.when(orderRepository.findById(order.getId())).thenReturn(Optional.empty());
        Mockito.when(paymentRepository.findByOrder(order)).thenReturn(Optional.of(payment));
        Assertions.assertThrows(OrderNotFoundException.class, () -> paymentService.create(paymentDto));
    }

    @Test
    @DisplayName("Should throw PaymentNotFoundException on update")
    public void shuldThrowPaymentNotFoundExceptionOnUpdate() {
        Assertions.assertThrows(PaymentNotFoundException.class, () -> paymentService.update(new PaymentUpdateDto(PaymentStatus.authorized, "123")));

    }

}
