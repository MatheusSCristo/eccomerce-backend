package com.matheus.commerce.service;

import com.matheus.commerce.domain.Product;
import com.matheus.commerce.domain.Rating;
import com.matheus.commerce.domain.User;
import com.matheus.commerce.dto.Rating.RatingDto;
import com.matheus.commerce.dto.product.ProductDto;
import com.matheus.commerce.infra.exceptions.ProductNotFoundException;
import com.matheus.commerce.repository.ProductRepository;
import com.matheus.commerce.repository.RatingRepository;
import com.matheus.commerce.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RatingRepository ratingRepository;

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

    public Product createRating(String productId,RatingDto ratingDto){
        Optional<Product> optionalProduct= productRepository.findById(productId);
        Optional<User> optionalUser=userRepository.findById(ratingDto.userId());
        if(optionalProduct.isEmpty() || optionalUser.isEmpty()) throw new ProductNotFoundException();
        Product product= optionalProduct.get();
        User user = optionalUser.get();
        Rating rating= new Rating(ratingDto,user,product);
        ratingRepository.save(rating);
        return product;
    }
}
