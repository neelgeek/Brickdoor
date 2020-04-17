package com.example.brickdoor.repositories;

import com.example.brickdoor.models.InterviewReview;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InterviewReviewRepository extends CrudRepository<InterviewReview, Integer> {

    @Query(value = "SELECT review FROM InterviewReview review")
    public List<InterviewReview> findAllInterviewReview();
}
