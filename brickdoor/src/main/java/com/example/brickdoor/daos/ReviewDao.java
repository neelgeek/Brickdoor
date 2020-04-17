package com.example.brickdoor.daos;

import com.example.brickdoor.models.InterviewReview;
import com.example.brickdoor.models.Review;
import com.example.brickdoor.models.WorkReview;
import com.example.brickdoor.repositories.InterviewReviewRepository;
import com.example.brickdoor.repositories.ReviewRepository;
import com.example.brickdoor.repositories.WorkReviewRepository;

import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewDao {

    @Autowired
    private InterviewReviewRepository interviewReviewRepo;

    @Autowired
    private WorkReviewRepository workReviewRepo;

    @Autowired
    private ReviewRepository reviewRepo;

    public List<InterviewReview> findAllInterviewReview() {
        return interviewReviewRepo.findAllInterviewReview();
    }

    public List<WorkReview> findAllWorkReview() {
        return workReviewRepo.findAllWorkReview();
    }

    public List<Review> findAllReview() {
        return reviewRepo.findAllReview();
    }

//    public InterviewReview createInterviewReview(InterviewReview review) {
//        return interviewReviewRepo.save(review);
//    }
//
//    public WorkReview createWorkReview(WorkReview review) {
//        return workReviewRepo.save(review);
//    }

    public Review createReview(Review review) {
        return reviewRepo.save(review);
    }

}
