package com.example.brickdoor.repositories;

import com.example.brickdoor.models.InterviewReview;
import com.example.brickdoor.models.Review;
import com.example.brickdoor.models.WorkReview;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InterviewReviewRepository extends CrudRepository<InterviewReview, Integer> {

    @Query(value = "SELECT review FROM InterviewReview review")
    public List<InterviewReview> findAllInterviewReview();


    @Query(value = "SELECT review FROM InterviewReview review JOIN review.company company WHERE company.id=:cId")
    public List<InterviewReview> findInterviewReviewsByCompanyId(@Param("cId") int cId);

    @Query(value = "SELECT review FROM InterviewReview review JOIN review.student student WHERE student.id=:sId")
    public List<InterviewReview> findInterviewReviewsByStudentId(@Param("sId") int studentId);

    @Query(value = "SELECT review FROM InterviewReview review where review.id=:wId")
    public InterviewReview findInterviewReviewById(@Param("wId") int wId);
}
