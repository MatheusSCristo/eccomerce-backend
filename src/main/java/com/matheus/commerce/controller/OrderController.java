package com.matheus.commerce.controller;

import com.matheus.commerce.domain.Order;
import com.matheus.commerce.domain.OrderProduct;
import com.matheus.commerce.dto.order.OrderDto;
import com.matheus.commerce.dto.order.OrderResponseDto;
import com.matheus.commerce.dto.order.OrderUpdateDto;
import com.matheus.commerce.dto.orderProduct.OrderProductDto;
import com.matheus.commerce.dto.orderProduct.OrderProductUpdateDto;
import com.matheus.commerce.service.OrderService;
import jakarta.validation.Valid;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> findAll() {
        List<OrderResponseDto> orderList = orderService.findAll();
        return ResponseEntity.ok().body(orderList);

    }
    @GetMapping("{clientId}")
    public ResponseEntity<List<OrderResponseDto>> findByClientId(@PathVariable String clientId){
        List<OrderResponseDto> orderList=orderService.findByClientId(clientId);
        return ResponseEntity.ok().body(orderList);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid OrderDto orderDto) {
        orderService.create(orderDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<OrderResponseDto> update(@RequestBody @Valid OrderUpdateDto orderUpdateDto, @PathVariable String id) {
        OrderResponseDto order = orderService.update(orderUpdateDto,id);
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String id) {
        orderService.delete(id);
        return ResponseEntity.ok().build();
    }

}
