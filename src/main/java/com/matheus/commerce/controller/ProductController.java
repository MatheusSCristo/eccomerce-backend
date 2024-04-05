package com.matheus.commerce.controller;

import com.matheus.commerce.domain.Product;
import com.matheus.commerce.dto.ProductDto;
import com.matheus.commerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> findAll(){
        List<Product> products= productService.findAll();
        return ResponseEntity.ok().body(products);
    }
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody ProductDto productDto){
        productService.create(productDto);
        return ResponseEntity.ok().build();
    }

}
