package com.matheus.commerce.service;

import com.matheus.commerce.domain.Order;
import com.matheus.commerce.domain.OrderProduct;
import com.matheus.commerce.domain.Product;
import com.matheus.commerce.dto.OrderDto;
import com.matheus.commerce.dto.OrderProductDto;
import com.matheus.commerce.repository.OrderProductRepository;
import com.matheus.commerce.repository.OrderRepository;
import com.matheus.commerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        List<OrderProduct> orderProductList=new ArrayList<>();
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

    private Integer calculateTotalInCents(List<OrderProduct> list) {
        Integer sum = 0;
        for (OrderProduct orderProduct : list) {
            sum += orderProduct.getSubtotalInCents();
        }
        return sum;
    }
}
