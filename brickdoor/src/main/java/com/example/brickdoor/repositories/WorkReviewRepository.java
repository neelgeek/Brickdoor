package com.example.brickdoor.repositories;

import com.example.brickdoor.models.WorkReview;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WorkReviewRepository extends CrudRepository<WorkReview, Integer> {

    @Query(value = "SELECT review FROM WorkReview review")
    public List<WorkReview> findAllWorkReview();
}
