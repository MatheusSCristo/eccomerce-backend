package com.matheus.commerce.service;

import com.matheus.commerce.domain.Order;
import com.matheus.commerce.domain.Product;
import com.matheus.commerce.domain.Rating;
import com.matheus.commerce.domain.User;
import com.matheus.commerce.dto.Rating.RatingDto;
import com.matheus.commerce.infra.exceptions.OrderNotFoundException;
import com.matheus.commerce.infra.exceptions.ProductNotFoundException;
import com.matheus.commerce.infra.exceptions.UserNotFoundException;
import com.matheus.commerce.repository.OrderRepository;
import com.matheus.commerce.repository.ProductRepository;
import com.matheus.commerce.repository.RatingRepository;
import com.matheus.commerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RatingService {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private OrderRepository orderRepository;


    public Product createRating(String productId, RatingDto ratingDto){
        Optional<Product> optionalProduct= productRepository.findById(productId);
        Optional<User> optionalUser=userRepository.findById(ratingDto.userId());
        Optional<Order> optionalOrder=orderRepository.findById(ratingDto.orderId());
        if(optionalProduct.isEmpty()) throw new ProductNotFoundException();
        if( optionalUser.isEmpty()) throw new UserNotFoundException();
        if(optionalOrder.isEmpty()) throw new OrderNotFoundException();
        Product product=optionalProduct.get();
        Rating rating= new Rating(ratingDto,optionalUser.get(),product,optionalOrder.get());
        ratingRepository.save(rating);
        return product;
    }
}
