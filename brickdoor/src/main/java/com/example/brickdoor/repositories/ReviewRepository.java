package com.example.brickdoor.repositories;

import com.example.brickdoor.models.Review;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Integer> {

    @Query(value = "SELECT review FROM Review review")
    public List<Review> findAllReview();
}
