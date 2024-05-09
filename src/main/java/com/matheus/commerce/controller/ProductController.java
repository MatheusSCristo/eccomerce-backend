package com.matheus.commerce.controller;

import com.matheus.commerce.domain.Product;
import com.matheus.commerce.domain.Rating;
import com.matheus.commerce.dto.Rating.RatingDto;
import com.matheus.commerce.dto.product.ProductDto;
import com.matheus.commerce.dto.product.ProductResponseDto;
import com.matheus.commerce.service.ProductService;

import javax.validation.Valid;

import com.matheus.commerce.service.RatingService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private RatingService ratingService;

    @GetMapping
    public ResponseEntity<Set<ProductResponseDto>> findAll() {
        List<Product> productList = productService.findAll();
        Set<ProductResponseDto> productResponseDtoList = new HashSet<>();
        for (Product product : productList) {
            ProductResponseDto productResponseDto = new ProductResponseDto(product);
            productResponseDtoList.add(productResponseDto);
        }
        return ResponseEntity.ok().body(productResponseDtoList);
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody @Valid ProductDto productDto) {
        productService.create(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<List<Product>> delete(@PathVariable String id) {
        List<Product> productList = productService.delete(id);
        return ResponseEntity.ok().body(productList);
    }

    @PostMapping("{id}")
    public ResponseEntity<Product> createRating(@PathVariable String id, @RequestBody RatingDto ratingDto){
        Product product= ratingService.createRating(id,ratingDto);
        return ResponseEntity.ok().build();
    }

}
