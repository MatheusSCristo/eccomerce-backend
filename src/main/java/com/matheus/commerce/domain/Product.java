package com.matheus.commerce.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.matheus.commerce.dto.product.ProductDto;
import com.matheus.commerce.enums.CategoryEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
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
    @Column(length=10485760)
    private String description;
    @Column(name = "price_in_cents")
    private Integer priceInCents;
    private Set<String> imagesUrl;
    private String brand;
    private String model;
    private Set<String> colors = new HashSet<>();
    private Set<Integer> sizes = new HashSet<>();
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<CategoryEnum> categories = new HashSet<>();
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<OrderProduct> orderProducts = new HashSet<>();
    @Column(name = "created_at")
    private LocalDate createdAt = LocalDate.now();
    @OneToMany(mappedBy = "product")
    private Set<Rating> ratings = new HashSet<>();

    public Product(ProductDto productDto) {
        this.brand = productDto.brand();
        this.categories = productDto.categories();
        this.colors = productDto.colors();
        this.description = productDto.description();
        this.imagesUrl = productDto.imagesUrl();
        this.name = productDto.name();
        this.model = productDto.model();
        this.priceInCents = productDto.priceInCents();
        this.sizes = productDto.sizes();
    }


    public Product(String id, ProductDto productDto) {
        this.id = id;
        this.brand = productDto.brand();
        this.categories = productDto.categories();
        this.colors = productDto.colors();
        this.description = productDto.description();
        this.imagesUrl = productDto.imagesUrl();
        this.name = productDto.name();
        this.model = productDto.model();
        this.priceInCents = productDto.priceInCents();
        this.sizes = productDto.sizes();
    }
}
