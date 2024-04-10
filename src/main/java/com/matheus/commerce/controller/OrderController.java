package com.matheus.commerce.controller;

import com.matheus.commerce.domain.Order;
import com.matheus.commerce.dto.order.OrderDto;
import com.matheus.commerce.dto.order.OrderResponseDto;
import com.matheus.commerce.service.OrderService;
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
    public ResponseEntity<List<OrderResponseDto>> findAll(){
        List<OrderResponseDto> orderList=orderService.findAll();
        return ResponseEntity.ok().body(orderList);

    }

    @PostMapping
    public ResponseEntity<Void> create(OrderDto orderDto){
        orderService.create(orderDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<OrderResponseDto> update(OrderDto orderDto, @PathVariable String id){
        OrderResponseDto order = orderService.update(orderDto,id);
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        orderService.delete(id);
        return ResponseEntity.ok().build();
    }

}
