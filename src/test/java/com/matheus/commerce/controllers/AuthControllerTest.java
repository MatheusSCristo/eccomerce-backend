package com.matheus.commerce.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.matheus.commerce.controller.AuthController;
import com.matheus.commerce.domain.User;
import com.matheus.commerce.dto.user.UserAccessResponseDto;
import com.matheus.commerce.dto.user.UserCreateDto;
import com.matheus.commerce.dto.user.UserLoginDto;
import com.matheus.commerce.enums.Role;
import com.matheus.commerce.infra.security.JwtAuthFilter;
import com.matheus.commerce.infra.security.JwtService;
import com.matheus.commerce.infra.security.SecurityConfiguration;
import com.matheus.commerce.service.AuthService;
import com.matheus.commerce.service.UserDetailsImp;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @MockBean
    private AuthService authService;
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
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {

    }

    @Test
    @DisplayName("Should register a new user")
    public void shouldRegisterANewUser() throws Exception {
        UserCreateDto userCreateDto = new UserCreateDto("John", "Doe", "30-12-2004", "john.doe@example.com", "password123", "987.654.321-00");
        User user = new User(userCreateDto, "12345");
        UserAccessResponseDto userAccessResponseDto = new UserAccessResponseDto(user, authService.getResponseOrder(user.getOrders()), "accessToken123");
        Mockito.when(authService.register(userCreateDto)).thenReturn(userAccessResponseDto);
        ResultActions response = mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(userCreateDto))
                .with(csrf())).andExpect(status().isOk());
        response.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.name").value(userCreateDto.name()));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.accessToken").value(userAccessResponseDto.accessToken()));
    }

    @Test
    @DisplayName("Should authenticate user")
    public void shouldAuthenticateUser() throws Exception {
        UserCreateDto userCreateDto = new UserCreateDto("John", "Doe", "30-12-2004", "john.doe@example.com", "password123", "987.654.321-00");
        User user = new User(userCreateDto, "12345");
        UserLoginDto userLoginDto=new UserLoginDto("john.doe@example.com","password123");
        UserAccessResponseDto userAccessResponseDto = new UserAccessResponseDto(user, authService.getResponseOrder(user.getOrders()), "accessToken123");
        Mockito.when(authService.authenticate(userLoginDto)).thenReturn(userAccessResponseDto);
        ResultActions response = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(userLoginDto))
                .with(csrf())).andExpect(status().isOk());
        response.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.name").value(userCreateDto.name()));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.accessToken").value(userAccessResponseDto.accessToken()));
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
