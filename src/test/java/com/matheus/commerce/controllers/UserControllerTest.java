package com.matheus.commerce.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.matheus.commerce.controller.ProductController;
import com.matheus.commerce.controller.UserController;
import com.matheus.commerce.domain.Order;
import com.matheus.commerce.domain.Rating;
import com.matheus.commerce.domain.User;
import com.matheus.commerce.dto.Rating.RatingDto;
import com.matheus.commerce.dto.order.OrderResponseDto;
import com.matheus.commerce.dto.user.UserResponseDto;
import com.matheus.commerce.dto.user.UserUpdateDto;
import com.matheus.commerce.infra.security.JwtAuthFilter;
import com.matheus.commerce.infra.security.JwtService;
import com.matheus.commerce.service.OrderService;
import com.matheus.commerce.service.UserDetailsImp;
import com.matheus.commerce.service.UserService;
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

import java.lang.reflect.Array;
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @MockBean
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;
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
    @DisplayName("Should find all users ")
    public void shouldFindAllUsers() throws Exception {
        User user1 = new User("1");
        User user2 = new User("2");
        User user3 = new User("3");
        List<UserResponseDto> userResponseDtoList = getUserResponseDtoList(List.of(user1, user2, user3));
        Mockito.when(userService.findAll()).thenReturn(userResponseDtoList);
        ResultActions response = mockMvc.perform(get("/users"));
        response.andExpect(MockMvcResultMatchers.status().is(200));
        response.andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
        response.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(userResponseDtoList.size())));
        response.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(user1.getId()));
        response.andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(user2.getId()));
        response.andExpect(MockMvcResultMatchers.jsonPath("$[2].id").value(user3.getId()));

    }
    @Test
    @DisplayName("Should find user by id ")
    public void shouldFindUserById() throws Exception {
        User user1 = new User("1");
        Mockito.when(userService.findById(user1.getId())).thenReturn(new UserResponseDto(user1,getResponseOrder(user1.getOrders()),getRatingDtoList(user1.getRatings())));
        ResultActions response = mockMvc.perform(get("/users/"+ user1.getId()));
        response.andExpect(MockMvcResultMatchers.status().is(200));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(user1.getId()));
    }


    @Test
    @DisplayName("Should update user")
    public void shouldUpdateUser() throws Exception {
        User user = new User("1");
        user.setName("Matheus");
        user.setEmail("matheus.cristo@outlook.com");
        UserUpdateDto userUpdateDto=new UserUpdateDto("Matheus",null,null,null,null,null,null,true);
        Mockito.when(userService.update(user.getId(),userUpdateDto)).thenReturn(new UserResponseDto(user,getResponseOrder(user.getOrders()),getRatingDtoList(user.getRatings())));
        ResultActions response = mockMvc.perform(put("/users/"+ user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userUpdateDto)));
        response.andExpect(MockMvcResultMatchers.status().is(200));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(user.getId()));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.name").value(user.getName()));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.email").value(user.getEmail()));
    }


    public List<UserResponseDto> getUserResponseDtoList(List<User> list) {
        List<UserResponseDto> userResponseDtoList = new ArrayList<>();
        for (User user : list) {
            UserResponseDto userResponseDto = new UserResponseDto(user, getResponseOrder(user.getOrders()),getRatingDtoList(user.getRatings()));
            userResponseDtoList.add(userResponseDto);
        }
        return userResponseDtoList;
    }

    public Set<OrderResponseDto> getResponseOrder(Set<Order> orders) {
        Set<OrderResponseDto> orderResponseDtoSet = new HashSet<>();
        for (Order order : orders) {
            orderResponseDtoSet.add(new OrderResponseDto(order));
        }
        return orderResponseDtoSet;
    }
    public Set<RatingDto> getRatingDtoList(Set<Rating> ratings){
        Set<RatingDto> ratingDtos=new HashSet<>();
        for(Rating rating:ratings){
            ratingDtos.add(new RatingDto(rating.getNumber(),rating.getComment(),rating.getUser().getId()));
        }
        return ratingDtos;

    }
}



