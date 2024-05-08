package com.matheus.commerce.repository;

import com.matheus.commerce.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating,String> {
}
