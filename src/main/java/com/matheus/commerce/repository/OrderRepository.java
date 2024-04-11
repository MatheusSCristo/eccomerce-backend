package com.matheus.commerce.repository;

import com.matheus.commerce.domain.Order;
import com.matheus.commerce.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,String> {
    Optional<List<Order>> findAllByUser(User client);
}
