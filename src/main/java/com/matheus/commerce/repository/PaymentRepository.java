package com.matheus.commerce.repository;

import com.matheus.commerce.domain.Order;
import com.matheus.commerce.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,String> {

    public Optional<Payment> findByOrder(Order order);
}
