package com.matheus.commerce.service;

import com.matheus.commerce.domain.Product;
import com.matheus.commerce.dto.product.ProductDto;
import com.matheus.commerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService  {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll(){
        return productRepository.findAll();

    }

    public void create(ProductDto productDto){
        Product product= new Product(productDto);
        productRepository.save(product);
    }


}
