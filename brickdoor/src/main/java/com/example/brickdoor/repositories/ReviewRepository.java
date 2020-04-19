package com.example.brickdoor.repositories;

import com.example.brickdoor.models.Review;
import com.example.brickdoor.models.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Integer> {

    @Query(value = "SELECT review FROM Review review")
    public List<Review> findAllReview();

    @Query(value = "SELECT review FROM Review review JOIN review.company company WHERE company.companyName=:cName")
    public List<Review> findReviewByCompanyName(@Param("cName") String companyName);

    @Query(value = "SELECT review FROM Review review JOIN review.company company WHERE company.id=:cId")
    public List<Review> findReviewByCompanyId(@Param("cId") int cId);

    @Query(value = "SELECT review FROM Review review JOIN review.student student WHERE student.username=:uName")
    public List<Review> findReviewByUsername(@Param("uName") String username);
}
