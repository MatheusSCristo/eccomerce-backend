package com.matheus.commerce.services;

import com.matheus.commerce.domain.*;
import com.matheus.commerce.dto.Rating.RatingDto;
import com.matheus.commerce.repository.*;
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
    private OrderProductRepository orderProductRepository;

    @Mock
    private RatingRepository ratingRepository;

    private RatingService ratingService;

    @BeforeEach
    void setup() {
        ratingService= new RatingService(productRepository,userRepository,ratingRepository,orderProductRepository);
    }

    @Test
    @DisplayName("Should create rating")
    public void shouldCreateRating(){
        Product product=new Product();
        User user= new User();
        OrderProduct orderProduct=new OrderProduct();
        RatingDto ratingDto= new RatingDto(user.getId(),3,"Bom",orderProduct.getId(),product.getId());
        Rating rating=new Rating(ratingDto,user,product,orderProduct);
        Mockito.when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(orderProductRepository.findById(orderProduct.getId())).thenReturn(Optional.of(orderProduct));
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
