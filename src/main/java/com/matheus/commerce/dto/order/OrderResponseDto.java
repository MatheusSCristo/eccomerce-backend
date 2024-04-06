package com.matheus.commerce.dto.order;

import com.matheus.commerce.domain.Order;
import com.matheus.commerce.domain.OrderProduct;
import com.matheus.commerce.dto.orderProduct.OrderProductResponseDto;
import com.matheus.commerce.dto.payment.PaymentResponseDto;
import com.matheus.commerce.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public class OrderResponseDto {
    private String id;
    private Integer totalInCents;
    private LocalDate createdAt;
    private OrderStatus orderStatus;
    private List<OrderProductResponseDto> orderProduct = new ArrayList<>();
    private PaymentResponseDto payment;

    public OrderResponseDto(Order order) {
        List<OrderProductResponseDto> orderProductResponseDtoList = new ArrayList<>();
        for (OrderProduct orderProduct : order.getOrderProduct()) {
            OrderProductResponseDto orderProductResponseDto = new OrderProductResponseDto(orderProduct);
            orderProductResponseDtoList.add(orderProductResponseDto);
        }
        this.orderProduct = orderProductResponseDtoList;
        this.id = order.getId();
        this.orderStatus = order.getOrderStatus();
        this.createdAt = order.getCreatedAt();
        this.totalInCents=order.getTotalInCents();
        this.payment= order.getPayment() !=null ? new PaymentResponseDto(order.getPayment()): null;

    }
}
