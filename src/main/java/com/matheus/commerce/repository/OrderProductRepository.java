package com.matheus.commerce.repository;

import com.matheus.commerce.domain.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct,String> {
}
