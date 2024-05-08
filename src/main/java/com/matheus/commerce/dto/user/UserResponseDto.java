package com.matheus.commerce.dto.user;

import com.matheus.commerce.domain.Order;
import com.matheus.commerce.domain.Rating;
import com.matheus.commerce.domain.User;
import com.matheus.commerce.dto.Rating.RatingDto;
import com.matheus.commerce.dto.order.OrderResponseDto;
import com.matheus.commerce.enums.Role;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public record UserResponseDto(String id, String name, String lastName, String birthDate, String email, Role role, String cpf, Set<OrderResponseDto> orders, Boolean verifiedEmail,
                             Set<RatingDto> ratings) {
    public UserResponseDto(User user, Set<OrderResponseDto> responseOrder,Set<RatingDto> ratings) {
        this(user.getId(), user.getName(), user.getLastName(), user.getBirthDate(), user.getEmail(), user.getRole(), user.getCpf(), responseOrder,user.isVerifiedEmail(),ratings);
    }


}
