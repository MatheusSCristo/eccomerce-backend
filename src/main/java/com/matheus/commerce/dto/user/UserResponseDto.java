package com.matheus.commerce.dto.user;

import com.matheus.commerce.domain.Order;
import com.matheus.commerce.domain.User;
import com.matheus.commerce.dto.order.OrderResponseDto;
import com.matheus.commerce.enums.Role;

import java.util.HashSet;
import java.util.Set;

public record UserResponseDto(String id, String name, String lastName, Integer age, String email, Role role, String cpf, Set<OrderResponseDto> orders) {
    public UserResponseDto(User user, Set<OrderResponseDto> responseOrder) {
        this(user.getId(), user.getName(), user.getLastName(), user.getAge(), user.getEmail(), user.getRole(), user.getCpf(), responseOrder);
    }


}
