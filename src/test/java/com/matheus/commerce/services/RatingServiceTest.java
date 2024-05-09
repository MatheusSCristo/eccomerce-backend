package com.matheus.commerce.services;

import com.matheus.commerce.domain.Order;
import com.matheus.commerce.domain.Product;
import com.matheus.commerce.domain.Rating;
import com.matheus.commerce.domain.User;
import com.matheus.commerce.dto.Rating.RatingDto;
import com.matheus.commerce.repository.OrderRepository;
import com.matheus.commerce.repository.ProductRepository;
import com.matheus.commerce.repository.RatingRepository;
import com.matheus.commerce.repository.UserRepository;
import com.matheus.commerce.service.RatingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.Set;

@SpringBootTest
public class RatingServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private RatingRepository ratingRepository;

    private RatingService ratingService;

    @BeforeEach
    void setup() {
        ratingService= new RatingService(productRepository,userRepository,ratingRepository,orderRepository);
    }

    @Test
    @DisplayName("Should create rating")
    public void shouldCreateRating(){
        Product product=new Product();
        User user= new User();
        Order order=new Order();
        RatingDto ratingDto= new RatingDto(user.getId(),3,"Bom",order.getId(),product.getId());
        Rating rating=new Rating(ratingDto,user,product,order);
        Mockito.when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        Mockito.doAnswer(invocation -> {
            product.setRatings(Set.of(rating));
            return null;
        }).when(ratingRepository).save(Mockito.any(Rating.class));
        Product responseProduct=ratingService.createRating(product.getId(),ratingDto);
        Assertions.assertEquals(responseProduct.getId(),product.getId());
        Assertions.assertEquals(responseProduct.getRatings().stream().findFirst().get().getUser(),user);
        Assertions.assertEquals(responseProduct.getRatings().stream().findFirst().get().getProduct().getId(),ratingDto.productId());




    }


}
