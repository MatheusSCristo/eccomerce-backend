package com.matheus.commerce.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.matheus.commerce.dto.Rating.RatingDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Length;
import org.hibernate.annotations.Type;

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
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @Column(length=10485760)
    private String comment;
    private Integer number;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private OrderProduct orderProduct;

    public Rating(RatingDto ratingDto,User user,Product product,OrderProduct orderProduct){
        this.user=user;
        this.product=product;
        this.comment=ratingDto.comment();
        this.number=ratingDto.rating();
        this.orderProduct=orderProduct;
    }

}
