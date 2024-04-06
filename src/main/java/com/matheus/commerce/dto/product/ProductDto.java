package com.matheus.commerce.dto.product;

import com.matheus.commerce.enums.CategoryEnum;
import jdk.jfr.Category;

import java.util.Set;

public record ProductDto(
    String name,
    String description,
    Integer priceInCents,
    String imageUrl,
    Double rating,
    String brand,
    String model,
    String color,
    Set<CategoryEnum> categories

){
}
