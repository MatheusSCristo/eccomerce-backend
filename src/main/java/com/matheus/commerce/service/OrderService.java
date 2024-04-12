package com.matheus.commerce.service;

import com.matheus.commerce.domain.Order;
import com.matheus.commerce.domain.OrderProduct;
import com.matheus.commerce.domain.Product;
import com.matheus.commerce.domain.User;
import com.matheus.commerce.dto.order.OrderDto;
import com.matheus.commerce.dto.order.OrderResponseDto;
import com.matheus.commerce.dto.order.OrderUpdateDto;
import com.matheus.commerce.dto.orderProduct.OrderProductDto;
import com.matheus.commerce.dto.orderProduct.OrderProductUpdateDto;
import com.matheus.commerce.repository.OrderProductRepository;
import com.matheus.commerce.repository.OrderRepository;
import com.matheus.commerce.repository.ProductRepository;
import com.matheus.commerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public List<OrderResponseDto> findAll() {
        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();
        List<Order> orderList = orderRepository.findAll();
        for (Order order : orderList) {
            orderResponseDtoList.add(new OrderResponseDto(order));
        }
        return orderResponseDtoList;
    }


    public void create(OrderDto orderDto) {
        Optional<User> optionalUser = userRepository.findById(orderDto.clientId());
        if (optionalUser.isPresent()) {
            User user=optionalUser.get();
            Order order = new Order(user);
            orderRepository.save(order);
            Set<OrderProduct> orderProductList = new HashSet<>();
            for (OrderProductDto orderProductDto : orderDto.products()) {
                Optional<Product> optionalProduct = productRepository.findById(orderProductDto.productId());
                if (optionalProduct.isPresent()) {
                    Product product = optionalProduct.get();
                    OrderProduct orderProduct = new OrderProduct(orderProductDto, product, order);
                    orderProductRepository.save(orderProduct);
                    orderProductList.add(orderProduct);
                }
            }
            order.setTotalInCents(calculateTotalInCents(orderProductList));
            orderRepository.save(order);
            Set<Order> userOrders=user.getOrders();
            userOrders.add(order);
            user.setOrders(userOrders);
            userRepository.save(user);
        }
    }

    private Integer calculateTotalInCents(Set<OrderProduct> list) {
        Integer sum = 0;
        for (OrderProduct orderProduct : list) {
            sum += orderProduct.getSubtotalInCents();
        }
        return sum;
    }

    public OrderResponseDto update(OrderUpdateDto orderUpdateDto, String id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setOrderStatus(orderUpdateDto.orderStatus());
            orderRepository.save(order);
            return new OrderResponseDto(order);
        }
        return null;
    }

    public void delete(String id) {
        orderRepository.deleteById(id);
    }

    public List<OrderResponseDto> findByClientId(String clientId) {
        Optional<User> optionalUser = userRepository.findById(clientId);
        if (optionalUser.isPresent()) {
            Optional<List<Order>> orderOptional = orderRepository.findAllByUser(optionalUser.get());
            List<OrderResponseDto> orders = new ArrayList<>();
            if (orderOptional.isPresent()) {
                for (Order order : orderOptional.get()) {
                    orders.add(new OrderResponseDto(order));
                }
            }
            return orders;
        }
        return null;
    }
}


