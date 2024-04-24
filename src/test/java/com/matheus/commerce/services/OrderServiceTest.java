package com.matheus.commerce.services;

import com.matheus.commerce.domain.BillingDetails;
import com.matheus.commerce.domain.Order;
import com.matheus.commerce.domain.Product;
import com.matheus.commerce.domain.User;
import com.matheus.commerce.dto.billingDetails.BillingDetailsDto;
import com.matheus.commerce.dto.order.OrderDto;
import com.matheus.commerce.dto.order.OrderResponseDto;
import com.matheus.commerce.dto.order.OrderUpdateDto;
import com.matheus.commerce.dto.orderProduct.OrderProductDto;
import com.matheus.commerce.dto.product.ProductDto;
import com.matheus.commerce.enums.CategoryEnum;
import com.matheus.commerce.enums.OrderStatus;
import com.matheus.commerce.infra.exceptions.OrderNotFoundException;
import com.matheus.commerce.infra.exceptions.ProductNotFoundException;
import com.matheus.commerce.infra.exceptions.UserNotFoundException;
import com.matheus.commerce.repository.OrderProductRepository;
import com.matheus.commerce.repository.OrderRepository;
import com.matheus.commerce.repository.ProductRepository;
import com.matheus.commerce.repository.UserRepository;
import com.matheus.commerce.service.OrderService;

import java.util.*;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderServiceTest {

    private OrderService orderService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderProductRepository orderProductRepository;

    private Product product;

    private OrderDto orderDto;

    @BeforeEach
    void setup() {
        orderDto = getOrderDto();
        orderService = new OrderService(orderRepository, orderProductRepository, productRepository, userRepository);
    }

    @Test
    @DisplayName("Should create order")
    public void shouldCreateOrder() {
        User user = new User("123");
        Mockito.when(userRepository.findById("123")).thenReturn(Optional.of(user));
        Mockito.when(productRepository.findById("12345")).thenReturn(Optional.ofNullable(product));
        Assertions.assertDoesNotThrow(() -> orderService.create(orderDto));
    }

    @Test
    @DisplayName("Should find all orders")
    public void shouldFindAllOrders() {
        List<Order> orders = new ArrayList<>(List.of(new Order(), new Order(), new Order()));
        Mockito.when(orderRepository.findAll()).thenReturn(orders);
        List<OrderResponseDto> orderResponseDtoList = orderService.findAll();
        Assertions.assertEquals(orderResponseDtoList.size(), orders.size());
        Assertions.assertEquals(orderResponseDtoList.get(0).getId(), orders.get(0).getId());
        Assertions.assertEquals(orderResponseDtoList.get(1).getId(), orders.get(1).getId());
        Assertions.assertEquals(orderResponseDtoList.get(2).getId(), orders.get(2).getId());
    }

    @Test
    @DisplayName("Should update order")
    public void shouldUpdateOrder() {
        Order order = new Order();
        Mockito.when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        OrderUpdateDto orderUpdateDto = new OrderUpdateDto(OrderStatus.delivered);
        OrderResponseDto orderResponseDto = orderService.update(orderUpdateDto, order.getId());
        Assertions.assertEquals(orderResponseDto.getId(), order.getId());
        Assertions.assertEquals(orderResponseDto.getOrderStatus(), OrderStatus.delivered);
    }

    @Test
    @DisplayName("Should find by clientId")
    public void shouldFindByClientId() {
        User user = new User();
        Order order1 = new Order(user);
        Order order2 = new Order(user);
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(orderRepository.findAllByUser(user)).thenReturn(Optional.of(new ArrayList<>(List.of(order1, order2))));
        List<OrderResponseDto> orders = orderService.findByClientId(user.getId());
        Assertions.assertEquals(orders.size(), 2);
        Assertions.assertEquals(orders.get(0).getId(), order1.getId());
        Assertions.assertEquals(orders.get(1).getId(), order2.getId());
    }

    @Test
    @DisplayName("Should throw UserNotFoundException on create order")
    public void shouldThrowUserNotFoundOnCreate() {
        Assertions.assertThrows(UserNotFoundException.class, () -> orderService.create(orderDto));
    }

    @Test
    @DisplayName("Should throw ProductNotFoundException on create order")
    public void shouldThrowProductNotFoundOnCreate() {
        User user = new User();
        Mockito.when(userRepository.findById(orderDto.clientId())).thenReturn(Optional.of(user));
        Assertions.assertThrows(ProductNotFoundException.class, () -> orderService.create(orderDto));
    }

    @Test
    @DisplayName("Should throw OrderNotFoundException on order update")
    public void shouldThrowProductNotFoundOnUpdate() {
        Assertions.assertThrows(OrderNotFoundException.class, () -> orderService.update(new OrderUpdateDto(OrderStatus.delivered), "123"));
    }

    @Test
    @DisplayName("Should throw UserNotFoundException on findByClient ")
    public void shouldThrowUserNotFoundExceptionOnFindByClient() {
        Assertions.assertThrows(UserNotFoundException.class, () -> orderService.findByClientId("123"));
    }

    @NotNull
    private OrderDto getOrderDto() {
        Set<CategoryEnum> categories = new HashSet<>();
        categories.add(CategoryEnum.security);
        product = new Product("12345", new ProductDto("X1000",
                "A powerful smartphone with advanced features.",
                9999,
                "https://example.com/smartphone.jpg",
                4.5,
                "ExampleBrand",
                "Smartphone",
                "Black",
                categories
        ));
        Set<OrderProductDto> orderProductDtos = new HashSet<>();
        orderProductDtos.add(new OrderProductDto(product.getId(), 3));
        return new OrderDto(orderProductDtos, "123", new BillingDetailsDto("Matheus", "Senas",
                "matheus.cristo@outlook.com","84999999", "123812831", "Natal", "Amintas", "Lagoa Nova"));
    }


}
