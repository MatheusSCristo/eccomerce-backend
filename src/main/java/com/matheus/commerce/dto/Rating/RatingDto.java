package com.matheus.commerce.dto.Rating;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RatingDto(@NotNull @Positive Integer rating, @NotNull String comment, @NotBlank String userId,@NotBlank String orderId,String productId) {

    public RatingDto(String userId,Integer rating,String comment,String orderId,String productId){
        this(rating,comment,userId,orderId,productId);
    }
}
