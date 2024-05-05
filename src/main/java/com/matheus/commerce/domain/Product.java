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
    private Set<String> imagesUrl;
    private Double rating;
    private String brand;
    private String model;
    private Set<String> colors=new HashSet<>();
    private Set<Integer> sizes=new HashSet<>();
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<CategoryEnum> categories = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<OrderProduct> orderProducts = new HashSet<>();

    public Product(ProductDto productDto) {
        this.brand = productDto.brand();
        this.categories = productDto.categories();
        this.colors = productDto.colors();
        this.description = productDto.description();
        this.imagesUrl = productDto.imagesUrl();
        this.name = productDto.name();
        this.model = productDto.model();
        this.priceInCents = productDto.priceInCents();
        this.rating = productDto.rating();
        this.sizes=productDto.sizes();
    }


    public Product(String id,ProductDto productDto) {
        this.id=id;
        this.brand = productDto.brand();
        this.categories = productDto.categories();
        this.colors = productDto.colors();
        this.description = productDto.description();
        this.imagesUrl = productDto.imagesUrl();
        this.name = productDto.name();
        this.model = productDto.model();
        this.priceInCents = productDto.priceInCents();
        this.rating = productDto.rating();
        this.sizes=productDto.sizes();
    }
}
