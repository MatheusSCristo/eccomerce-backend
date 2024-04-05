package com.matheus.commerce.dto;

import java.util.Set;

public record OrderDto(Set<OrderProductDto> orderProductDto) {
}
