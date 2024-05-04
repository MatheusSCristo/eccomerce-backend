package com.matheus.commerce.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matheus.commerce.controller.OrderController;
import com.matheus.commerce.controller.ProductController;
import com.matheus.commerce.domain.Product;
import com.matheus.commerce.dto.product.ProductDto;
import com.matheus.commerce.enums.CategoryEnum;
import com.matheus.commerce.enums.OrderStatus;
import com.matheus.commerce.infra.security.JwtAuthFilter;
import com.matheus.commerce.infra.security.JwtService;
import com.matheus.commerce.service.OrderService;
import com.matheus.commerce.service.ProductService;
import com.matheus.commerce.service.UserDetailsImp;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;
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

    private ProductDto productDto1;
    private ProductDto productDto2;
    Product product1 ;
    Product product2 ;

    @BeforeEach
    void setup() {
        product2 = new Product(new ProductDto("Headphone", "Great", 60000, "", 4.5, "Multilaser", "P300", Set.of("Black"), Set.of(CategoryEnum.casual, CategoryEnum.kids, CategoryEnum.fashion),Set.of(32,33,35)));
        product1 = new Product(new ProductDto("Veja", "Limpador", 3000, "", 3.7, "Veja", "Veja",  Set.of("Blue"), Set.of(CategoryEnum.casual),Set.of(32,33,35)));
        product1.setId("1");
        product2.setId("2");
    }

    @Test
    @DisplayName("Should find all products")
    public void shouldFindAllProducts() throws Exception {
        List<Product> productList = new ArrayList<>(List.of(product1, product2));
        Mockito.when(productService.findAll()).thenReturn(productList);
        ResultActions response = mockMvc.perform(get("/products"));
        response.andExpect(MockMvcResultMatchers.status().is(200));
        response.andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
        response.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(productList.size())));


    }

    @Test
    @DisplayName("Should delete product")
    public void shouldDeleteProduct() throws Exception {
        List<Product> productList = new ArrayList<>(List.of(product2));
        Mockito.when(productService.delete(product1.getId())).thenReturn(productList);
        ResultActions response = mockMvc.perform(delete("/products/" + product1.getId()));
        response.andExpect(MockMvcResultMatchers.status().is(200));
        response.andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
        response.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(productList.size())));
        response.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(product2.getId()));
        response.andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(product2.getName()));
    }
}
