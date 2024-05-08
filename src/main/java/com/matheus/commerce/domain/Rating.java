package com.matheus.commerce.domain;

import com.matheus.commerce.dto.Rating.RatingDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "ratings")
@Table(name = "ratings")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
    @ManyToOne
    private User user;
    private String comment;
    private Integer number;

    public Rating(RatingDto ratingDto,User user,Product product){
        this.user=user;
        this.product=product;
        this.comment=ratingDto.comment();
        this.number=ratingDto.rating();
    }

}
