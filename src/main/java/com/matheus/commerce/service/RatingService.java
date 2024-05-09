package com.matheus.commerce.service;

import com.matheus.commerce.domain.*;
import com.matheus.commerce.dto.Rating.RatingDto;
import com.matheus.commerce.infra.exceptions.OrderNotFoundException;
import com.matheus.commerce.infra.exceptions.ProductNotFoundException;
import com.matheus.commerce.infra.exceptions.UserNotFoundException;
import com.matheus.commerce.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RatingService {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;


    public Product createRating(String productId, RatingDto ratingDto){
        Optional<Product> optionalProduct= productRepository.findById(productId);
        Optional<User> optionalUser=userRepository.findById(ratingDto.userId());
        Optional<OrderProduct> optionalOrderProduct=orderProductRepository.findById(ratingDto.orderProductId());
        if(optionalProduct.isEmpty()) throw new ProductNotFoundException();
        if( optionalUser.isEmpty()) throw new UserNotFoundException();
        if(optionalOrderProduct.isEmpty()) throw new OrderNotFoundException();
        Product product=optionalProduct.get();
        Rating rating= new Rating(ratingDto,optionalUser.get(),product,optionalOrderProduct.get());
        ratingRepository.save(rating);
        return product;
    }
}
