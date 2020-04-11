package com.example.brickdoor.repositories;

import com.example.brickdoor.models.Review;

import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, Integer> {
}
