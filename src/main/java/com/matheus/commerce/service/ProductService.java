package com.matheus.commerce.service;

import com.matheus.commerce.domain.Product;
import com.matheus.commerce.dto.product.ProductDto;
import com.matheus.commerce.infra.exceptions.ProductNotFoundException;
import com.matheus.commerce.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public void create(ProductDto productDto) {
        Product product = new Product(productDto);
        productRepository.save(product);
    }

    public List<Product> delete(String id) {
        try {
            productRepository.deleteById(id);
        } catch (IllegalArgumentException exception) {
            throw new ProductNotFoundException();
        }
        return findAll();
    }
}
