package com.matheus.commerce.domain;

import com.matheus.commerce.dto.OrderProductDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_product")
@Entity(name="order_product")
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;
    private Integer quantity;
    private Integer subtotalInCents;
    private String imageUrl;

    public OrderProduct(OrderProductDto orderProductDTO, Product product,Order order){
        this.product=product;
        this.quantity=orderProductDTO.quantity();
        this.subtotalInCents=calculateSubTotalInCents(product,quantity);
        this.order=order;
        this.imageUrl=product.getImageUrl();
    }



    private Integer calculateSubTotalInCents(Product product,Integer quantity){
        return product.getPriceInCents() * quantity;
    }
}
