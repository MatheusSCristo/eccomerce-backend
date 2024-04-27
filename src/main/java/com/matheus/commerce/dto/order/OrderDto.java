package com.matheus.commerce.dto.order;

import com.matheus.commerce.dto.billingDetails.BillingDetailsDto;
import com.matheus.commerce.dto.orderProduct.OrderProductDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.ToString;

import java.util.Set;

public record OrderDto(@NotNull Set<OrderProductDto> products, @NotBlank String clientId, @NotNull BillingDetailsDto billingDetailsDto,@NotNull Integer shippingFeeInCents) {
}
