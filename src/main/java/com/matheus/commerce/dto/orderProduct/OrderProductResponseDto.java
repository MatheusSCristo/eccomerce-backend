package com.matheus.commerce.dto.orderProduct;

import com.matheus.commerce.domain.OrderProduct;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderProductResponseDto {
    private String id;
    private String productId;
    private Integer quantity;
    private Integer subtotalInCents;
    private String imageUrl;

    public OrderProductResponseDto(OrderProduct orderProduct) {
        this.id = orderProduct.getId();
        this.productId = orderProduct.getProduct().getId();
        this.subtotalInCents = orderProduct.getSubtotalInCents();
        this.quantity = orderProduct.getQuantity();
        this.imageUrl = orderProduct.getImageUrl();
    }
}
