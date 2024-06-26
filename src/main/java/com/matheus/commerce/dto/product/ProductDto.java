package com.matheus.commerce.dto.product;

import com.matheus.commerce.domain.Rating;
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
        @NotNull
        Set<String> imagesUrl,
        @NotNull
        Double rating,
        @NotBlank
        String brand,
        @NotBlank
        String model,
        @NotNull
        Set<String> colors,
        @NotNull
        Set<CategoryEnum> categories,
        @NotNull
        Set<Integer> sizes

) {

}
