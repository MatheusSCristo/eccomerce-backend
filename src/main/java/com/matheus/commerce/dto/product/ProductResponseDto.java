package com.matheus.commerce.dto.product;

import com.matheus.commerce.domain.Product;
import com.matheus.commerce.domain.Rating;
import com.matheus.commerce.dto.Rating.RatingDto;
import com.matheus.commerce.enums.CategoryEnum;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
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
    private Set<RatingDto> ratings;
    private String brand;
    private String model;
    private Set<String> colors;
    private Set<CategoryEnum> categories = new HashSet<>();
    private Set<Integer> sizes=new HashSet<>();
    private LocalDate createdAt;

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
        this.ratings = getRatingDtoList(product.getRatings()) ;
        this.sizes=product.getSizes();
        this.createdAt=product.getCreatedAt();
    }

    private Set<RatingDto> getRatingDtoList(Set<Rating> ratings){
        Set<RatingDto> ratingDtos=new HashSet<>();
        for(Rating rating:ratings){
            ratingDtos.add(new RatingDto(rating.getNumber(), rating.getComment(), rating.getUser().getId(), rating.getOrderProduct().getId(),rating.getProduct().getId()));
        }
        return ratingDtos;
    }



}
