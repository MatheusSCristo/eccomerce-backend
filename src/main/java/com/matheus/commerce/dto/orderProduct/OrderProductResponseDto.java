package com.matheus.commerce.dto.orderProduct;

import com.matheus.commerce.domain.OrderProduct;
import com.matheus.commerce.domain.Rating;
import com.matheus.commerce.dto.Rating.RatingDto;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class OrderProductResponseDto {
    private String id;
    private String productId;
    private Integer quantity;
    private Integer subtotalInCents;
    private Set<String> imagesUrl;
    private RatingDto rating;


    public OrderProductResponseDto(OrderProduct orderProduct) {
        this.id = orderProduct.getId();
        this.productId = orderProduct.getProduct().getId();
        this.subtotalInCents = orderProduct.getSubtotalInCents();
        this.quantity = orderProduct.getQuantity();
        this.imagesUrl = orderProduct.getImagesUrl();
        this.rating = orderProduct.getRating() != null ? new RatingDto(orderProduct.getRating().getNumber(), orderProduct.getRating().getComment(), orderProduct.getRating().getUser().getId(), orderProduct.getRating().getOrderProduct().getId(), orderProduct.getRating().getProduct().getId()) : null;
    }

}
