package com.matheus.commerce.dto.user;

import com.matheus.commerce.domain.Order;
import com.matheus.commerce.domain.User;
import com.matheus.commerce.dto.order.OrderResponseDto;
import com.matheus.commerce.enums.Role;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public record UserAccessResponseDto(String id, String name, String lastName, String birthDate, String email, Role role, String cpf, Set<OrderResponseDto> orders, String accessToken,boolean verifiedEmail) {
    public UserAccessResponseDto(User user, Set<OrderResponseDto> responseOrder,String accessToken) {
        this(user.getId(), user.getName(), user.getLastName(), user.getBirthDate(), user.getEmail(), user.getRole(), user.getCpf(), responseOrder,accessToken,user.isVerifiedEmail());
    }


}
