package com.matheus.commerce.dto.product;

import com.matheus.commerce.enums.CategoryEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;

public record ProductDto(
        @NotBlank
        String name,
        @NotBlank
        String description,
        @NotNull
        Integer priceInCents,
        @NotBlank
        String imageUrl,
        @NotNull
        Double rating,
        @NotBlank
        String brand,
        @NotBlank
        String model,
        @NotBlank
        String color,
        @NotNull
        Set<CategoryEnum> categories

) {
}
