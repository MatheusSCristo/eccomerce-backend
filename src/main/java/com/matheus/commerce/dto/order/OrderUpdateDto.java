package com.matheus.commerce.dto.order;

import com.matheus.commerce.dto.orderProduct.OrderProductDto;
import com.matheus.commerce.enums.OrderStatus;

import java.util.Set;

public record OrderUpdateDto(OrderStatus orderStatus, Set<OrderProductDto> products) {
}
