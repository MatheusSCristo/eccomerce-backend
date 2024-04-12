package com.matheus.commerce.dto.orderProduct;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record OrderProductDto(@NotBlank String productId, @NotNull Integer quantity) {
}
