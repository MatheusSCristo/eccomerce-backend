package com.matheus.commerce.service;

import com.matheus.commerce.domain.Order;
import com.matheus.commerce.domain.OrderProduct;
import com.matheus.commerce.domain.Product;
import com.matheus.commerce.dto.order.OrderDto;
import com.matheus.commerce.dto.orderProduct.OrderProductDto;
import com.matheus.commerce.repository.OrderProductRepository;
import com.matheus.commerce.repository.OrderRepository;
import com.matheus.commerce.repository.ProductRepository;
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

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public void create(OrderDto orderDto) {
        Order order = new Order();
        orderRepository.save(order);
        Set<OrderProduct> orderProductList = new HashSet<>();
        for (OrderProductDto orderProductDto : orderDto.orderProductDto()) {
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
    }

    private Integer calculateTotalInCents(Set<OrderProduct> list) {
        Integer sum = 0;
        for (OrderProduct orderProduct : list) {
            sum += orderProduct.getSubtotalInCents();
        }
        return sum;
    }

    public void update(OrderDto orderDto, String id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            Set<OrderProduct> orderProductList = order.getOrderProduct();
            for (OrderProductDto orderProductDto : orderDto.orderProductDto()) {
                Optional<Product> optionalProduct = productRepository.findById(orderProductDto.productId());
                if (optionalProduct.isPresent()) {
                    Product product = optionalProduct.get();
                    Optional<OrderProduct> orderProductOptional = orderProductRepository.findByProduct(product);
                    if (orderProductOptional.isPresent()) {
                        OrderProduct tempOrderProduct = orderProductOptional.get();
                        tempOrderProduct.setQuantity(tempOrderProduct.getQuantity() + orderProductDto.quantity());
                        tempOrderProduct.setSubtotalInCents(tempOrderProduct.getProduct().getPriceInCents() * tempOrderProduct.getQuantity());
                        orderProductRepository.save(tempOrderProduct);

                    } else {
                        OrderProduct orderProduct = new OrderProduct(orderProductDto, product, order);
                        orderProductRepository.save(orderProduct);
                        orderProductList.add(orderProduct);
                    }
                }
                order.setTotalInCents(calculateTotalInCents(orderProductList));
                order.setOrderProduct(orderProductList);
                orderRepository.save(order);
            }
        }

    }
}
