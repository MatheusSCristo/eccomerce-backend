package com.matheus.commerce.dto.order;

import com.matheus.commerce.dto.orderProduct.OrderProductDto;

import java.util.Set;

public record OrderDto(Set<OrderProductDto> products,String clientId) {
}
