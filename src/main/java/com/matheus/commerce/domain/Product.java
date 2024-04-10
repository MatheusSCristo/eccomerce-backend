package com.matheus.commerce.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.matheus.commerce.dto.product.ProductDto;
import com.matheus.commerce.enums.CategoryEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "product")
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String description;
    @Column(name = "price_in_cents")
    private Integer priceInCents;
    private String imageUrl;
    private Double rating;
    private String brand;
    private String model;
    private String color;
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<CategoryEnum> categories = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<OrderProduct> orderProducts = new HashSet<>();

    public Product(ProductDto productDto) {
        this.brand = productDto.brand();
        this.categories = productDto.categories();
        this.color = productDto.color();
        this.description = productDto.description();
        this.imageUrl = productDto.imageUrl();
        this.name = productDto.name();
        this.model = productDto.model();
        this.priceInCents = productDto.priceInCents();
        this.rating = productDto.rating();
    }


}
