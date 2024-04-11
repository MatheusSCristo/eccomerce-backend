package com.matheus.commerce.service;

import com.matheus.commerce.domain.Order;
import com.matheus.commerce.domain.OrderProduct;
import com.matheus.commerce.domain.Product;
import com.matheus.commerce.dto.order.OrderDto;
import com.matheus.commerce.dto.order.OrderResponseDto;
import com.matheus.commerce.dto.orderProduct.OrderProductDto;
import com.matheus.commerce.dto.orderProduct.OrderProductUpdateDto;
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

    public List<OrderResponseDto> findAll() {
        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();
        List<Order> orderList = orderRepository.findAll();
        for (Order order : orderList) {
            orderResponseDtoList.add(new OrderResponseDto(order));
        }
        return orderResponseDtoList;
    }

    public void create(OrderDto orderDto) {
        Order order = new Order();
        System.out.println(orderDto.products());
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
    }

    private Integer calculateTotalInCents(Set<OrderProduct> list) {
        Integer sum = 0;
        for (OrderProduct orderProduct : list) {
            sum += orderProduct.getSubtotalInCents();
        }
        return sum;
    }

    public OrderResponseDto update(OrderDto orderDto, String id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            Set<OrderProduct> orderProductList = order.getOrderProduct();
            for (OrderProductDto orderProductDto : orderDto.products()) {
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
                return new OrderResponseDto(order);
            }
        }
        return null;
    }

    public void delete(String id) {
        orderRepository.deleteById(id);
    }


    public OrderResponseDto deleteOneOrderProduct(String id, String productOrderId) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            Set<OrderProduct> orderProductList = order.getOrderProduct();
            orderProductList.removeIf(orderProduct -> Objects.equals(orderProduct.getId(), productOrderId));
            order.setOrderProduct(orderProductList);
            orderRepository.save(order);
            orderProductRepository.deleteById(productOrderId);
            return new OrderResponseDto(order);
        }
        return null;
    }

    public OrderResponseDto updateOneOrderProduct(OrderProductUpdateDto orderProductUpdateDto, String id, String productOrderId) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            Set<OrderProduct> orderProductList = order.getOrderProduct();
            Optional<OrderProduct> orderProductOptional = findOrderProduct(productOrderId, orderProductList);
            if (orderProductOptional.isPresent()) {
                orderProductList.removeIf(orderProduct -> Objects.equals(orderProduct.getId(), productOrderId));
                OrderProduct orderProduct = orderProductOptional.get();
                orderProduct.setQuantity(orderProductUpdateDto.quantity());
                orderProduct.setSubtotalInCents(orderProductUpdateDto.quantity() * orderProduct.getProduct().getPriceInCents());
                orderProductList.add(orderProduct);
                order.setOrderProduct(orderProductList);
                orderRepository.save(order);
                orderProductRepository.save(orderProduct);
            }
            return new OrderResponseDto(order);
        }
        return null;
    }

    private Optional<OrderProduct> findOrderProduct(String productOrderId, Set<OrderProduct> orderProductList) {
        return orderProductList.stream()
                .filter(orderProduct -> Objects.equals(orderProduct.getId(), productOrderId))
                .findFirst();
    }
}


