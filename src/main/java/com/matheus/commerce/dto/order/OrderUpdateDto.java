package com.matheus.commerce.dto.order;

import com.matheus.commerce.dto.orderProduct.OrderProductDto;
import com.matheus.commerce.enums.OrderStatus;

import javax.validation.constraints.NotBlank;
import java.util.Set;
public record OrderUpdateDto(@NotBlank OrderStatus orderStatus, Set<OrderProductDto> products) {
}
