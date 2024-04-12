package com.matheus.commerce.dto.order;

import com.matheus.commerce.dto.orderProduct.OrderProductDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record OrderDto(@NotNull Set<OrderProductDto> products, @NotBlank String clientId) {
}
