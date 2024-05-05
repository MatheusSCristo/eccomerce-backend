package com.matheus.commerce.dto.product;

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
    private Set<String> imagesUrl;
    private Double rating;
    private String brand;
    private String model;
    private Set<String> colors;
    private Set<CategoryEnum> categories = new HashSet<>();
    private Set<Integer> sizes=new HashSet<>();
    public ProductResponseDto(Product product){
        this.id=product.getId();
        this.brand = product.getBrand();
        this.categories = product.getCategories();
        this.colors = product.getColors();
        this.description = product.getDescription();
        this.imagesUrl = product.getImagesUrl();
        this.name = product.getName();
        this.model = product.getModel();
        this.priceInCents = product.getPriceInCents();
        this.rating = product.getRating();
        this.sizes=product.getSizes();
    }



}
