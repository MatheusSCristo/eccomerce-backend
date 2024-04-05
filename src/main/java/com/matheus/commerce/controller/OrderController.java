package com.matheus.commerce.controller;

import com.matheus.commerce.domain.Order;
import com.matheus.commerce.dto.OrderDto;
import com.matheus.commerce.dto.OrderResponseDto;
import com.matheus.commerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody OrderDto orderDto){
        orderService.create(orderDto);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> findAll(){
        List<Order> orders=orderService.findAll();
        List<OrderResponseDto> orderResponseDtoList=new ArrayList<>();
        for(Order order:orders){
            OrderResponseDto orderResponseDto=new OrderResponseDto(order);
            orderResponseDtoList.add(orderResponseDto);
        }
        return ResponseEntity.ok().body(orderResponseDtoList);
    }


}
