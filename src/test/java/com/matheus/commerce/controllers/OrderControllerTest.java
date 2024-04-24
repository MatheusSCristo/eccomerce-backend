package com.matheus.commerce.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matheus.commerce.controller.AuthController;
import com.matheus.commerce.controller.OrderController;
import com.matheus.commerce.domain.Order;
import com.matheus.commerce.domain.OrderProduct;
import com.matheus.commerce.domain.Product;
import com.matheus.commerce.dto.billingDetails.BillingDetailsDto;
import com.matheus.commerce.dto.order.OrderDto;
import com.matheus.commerce.dto.order.OrderResponseDto;
import com.matheus.commerce.dto.order.OrderUpdateDto;
import com.matheus.commerce.dto.orderProduct.OrderProductDto;
import com.matheus.commerce.dto.product.ProductDto;
import com.matheus.commerce.enums.OrderStatus;
import com.matheus.commerce.infra.security.JwtAuthFilter;
import com.matheus.commerce.infra.security.JwtService;
import com.matheus.commerce.service.OrderService;
import com.matheus.commerce.service.UserDetailsImp;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private UserDetailsImp userDetailsImp;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private JwtAuthFilter jwtAuthFilter;
    @Test
    @DisplayName("Should create a order")
    public void shouldCreateOrder() throws JsonProcessingException,Exception{
        Product product = new Product(new ProductDto("Veja", "Limpador", 2000, "", 2.3, "Veja", "Veja", "Transparent", Set.of()));
        OrderProductDto orderProductDto= new OrderProductDto(product.getId(),4);
        OrderDto orderDto = new OrderDto(Set.of(orderProductDto),"12345",new BillingDetailsDto("Matheus", "Senas",
                "matheus.cristo@outlook.com","84999999", "123812831", "Natal", "Amintas", "Lagoa Nova"));
        ResultActions response=mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(orderDto))
                .with(csrf()));
        response.andExpect(MockMvcResultMatchers.status().is(201));
    }
    @Test
    @DisplayName("Should find all orders")
    public void shouldFindAllOrders() throws Exception{
        OrderResponseDto orderResponseDto1=new OrderResponseDto(new Order());
        OrderResponseDto orderResponseDto2=new OrderResponseDto(new Order());
        orderResponseDto1.setId("1");
        orderResponseDto2.setId("2");
        List<OrderResponseDto> orderResponseDtoList=List.of(orderResponseDto1,orderResponseDto2);
        Mockito.when(orderService.findAll()).thenReturn(orderResponseDtoList);
        ResultActions response=mockMvc.perform(get("/orders").with(csrf()));
        response.andExpect(MockMvcResultMatchers.status().is(200));
        response.andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
        response.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(orderResponseDtoList.size())));
        response.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(orderResponseDto1.getId()));
        response.andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(orderResponseDto2.getId()));
    }
    @Test
    @DisplayName("Should find by ClientId")
    public void shouldFindByClientId() throws Exception{
        OrderResponseDto orderResponseDto1=new OrderResponseDto(new Order());
        OrderResponseDto orderResponseDto2=new OrderResponseDto(new Order());
        orderResponseDto1.setId("1");
        orderResponseDto2.setId("2");
        List<OrderResponseDto> orderResponseDtoList=List.of(orderResponseDto1,orderResponseDto2);
        Mockito.when(orderService.findByClientId("12345")).thenReturn(orderResponseDtoList);
        ResultActions response=mockMvc.perform(get("/orders/12345").with(csrf()));
        response.andExpect(MockMvcResultMatchers.status().is(200));
        response.andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
        response.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(orderResponseDtoList.size())));
        response.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(orderResponseDto1.getId()));
        response.andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(orderResponseDto2.getId()));
    }
    @Test
    @DisplayName("Should update order")
    public void shouldUpdateOrder() throws Exception{
        OrderResponseDto orderResponseDto1=new OrderResponseDto(new Order());
        OrderUpdateDto orderUpdateDto=new OrderUpdateDto(OrderStatus.delivered);
        orderResponseDto1.setId("1");
        orderResponseDto1.setOrderStatus(OrderStatus.delivered);
        Mockito.when(orderService.update(orderUpdateDto,orderResponseDto1.getId())).thenReturn(orderResponseDto1);
        ResultActions response=mockMvc.perform(put("/orders/"+orderResponseDto1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(orderUpdateDto))
                .with(csrf()));
        response.andExpect(MockMvcResultMatchers.status().is(200));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(orderResponseDto1.getId()));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.orderStatus").value(orderResponseDto1.getOrderStatus().toString()));
    }
}
