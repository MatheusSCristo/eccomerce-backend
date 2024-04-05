package com.matheus.commerce.dto;

import com.matheus.commerce.domain.Product;
import com.matheus.commerce.enums.CategoryEnum;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ProductResponseDto {
    private String id;
    private String name;
    private String description;
    private Integer priceInCents;
    private String imageUrl;
    private Double rating;
    private String brand;
    private String model;
    private String color;
    private Set<CategoryEnum> categories = new HashSet<>();

    public ProductResponseDto(Product product){
        this.id=product.getId();
        this.brand = product.getBrand();
        this.categories = product.getCategories();
        this.color = product.getColor();
        this.description = product.getDescription();
        this.imageUrl = product.getImageUrl();
        this.name = product.getName();
        this.model = product.getModel();
        this.priceInCents = product.getPriceInCents();
        this.rating = product.getRating();
    }



}
