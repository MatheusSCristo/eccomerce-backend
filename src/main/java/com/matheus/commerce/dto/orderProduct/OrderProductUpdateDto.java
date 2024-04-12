package com.matheus.commerce.dto.orderProduct;

import jakarta.validation.constraints.NotNull;

public record OrderProductUpdateDto(@NotNull Integer quantity) {
}
